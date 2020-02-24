package com.analysis.report.pdf;

import com.analysis.report.charts.generator.DefaultChartGenerator;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;
import com.tushare.constant.index.IndexDailyBasic;
import com.tushare.constant.interest.IntrestRateFields;
import com.tushare.exception.TushareException;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class IndexPdfGenerator {

    public static Map<String, String> uqerIndexMap = new HashMap<>();

    static {
        uqerIndexMap.put("000009","上证380");
        uqerIndexMap.put("000010","上证180");
        uqerIndexMap.put("000016","上证50");
        uqerIndexMap.put("000068","上证资源");
        uqerIndexMap.put("000300","沪深300");
        uqerIndexMap.put("000807","中证食品饮料");
        uqerIndexMap.put("000827","中证环保");
        uqerIndexMap.put("000852","中证1000");
        uqerIndexMap.put("000903","中证100");
        uqerIndexMap.put("000905","中证500");
        uqerIndexMap.put("000906","中证800");
        uqerIndexMap.put("000922","中证红利");
        uqerIndexMap.put("000925","中证基本面50");
        uqerIndexMap.put("000928","中证能源");
        uqerIndexMap.put("000932","中证消费");
        uqerIndexMap.put("000933","中证医药");
        uqerIndexMap.put("000934","中证金融");
        uqerIndexMap.put("000944","中证内地资源");
        uqerIndexMap.put("000986","中证全指能源");
        uqerIndexMap.put("000987","中证全指材料");
        uqerIndexMap.put("000988","中证全指工业");
        uqerIndexMap.put("000989","中证全指可选");
        uqerIndexMap.put("000990","中证全指消费");
        uqerIndexMap.put("000991","中证全指医药");
        uqerIndexMap.put("000992","中证全指金融");
        uqerIndexMap.put("000993","中证全指信息");
        uqerIndexMap.put("000998","中证TMT");
        uqerIndexMap.put("399001","深证成指");
        uqerIndexMap.put("399005","深证中小板指");
        uqerIndexMap.put("399006","深证创业板指");
        uqerIndexMap.put("399101","深证中小板综");
        uqerIndexMap.put("399102","深证创业板综");
        uqerIndexMap.put("399324","深证红利");
        uqerIndexMap.put("399330","深证100");
        uqerIndexMap.put("399396","国证食品");
        uqerIndexMap.put("399550","国证央视50");
        uqerIndexMap.put("399606","深证创业板R");
        uqerIndexMap.put("399610","深证TMT50");
        uqerIndexMap.put("399812","中证养老产业");
        uqerIndexMap.put("399814","中证大农业");
        uqerIndexMap.put("399959","中证军工指数");
        uqerIndexMap.put("399971","中证传媒");
        uqerIndexMap.put("399983","中证地产等权");
        uqerIndexMap.put("399997","中证白酒");
        uqerIndexMap.put("399998","中证煤炭");
    }

    public static PdfPTable fullFillUqerIndexTable(String pathPrefix) throws DocumentException, IOException {
        //方法一：使用Windows系统字体(TrueType)
        //http://blog.csdn.net/ol_beta/article/details/5926451
        BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/simfang.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        PdfPTable pdfPTable = new PdfPTable(12);
        PdfPHeaderCell code=new PdfPHeaderCell();
        code.setPhrase(new Phrase("指数代码",font));
        pdfPTable.addCell(code);
        PdfPHeaderCell name=new PdfPHeaderCell();
        name.setPhrase(new Phrase("指数名称",font));
        pdfPTable.addCell(name);
        PdfPHeaderCell pe=new PdfPHeaderCell();
        pe.setPhrase(new Phrase("pe-ttm"));
        pdfPTable.addCell(pe);
        PdfPHeaderCell peLow=new PdfPHeaderCell();
        peLow.setPhrase(new Phrase("pe-low"));
        pdfPTable.addCell(peLow);
        PdfPHeaderCell peMedian=new PdfPHeaderCell();
        peMedian.setPhrase(new Phrase("pe-median"));
        pdfPTable.addCell(peMedian);
        PdfPHeaderCell peHigh=new PdfPHeaderCell();
        peHigh.setPhrase(new Phrase("pe-high"));
        pdfPTable.addCell(peHigh);
        PdfPHeaderCell peKelly=new PdfPHeaderCell();
        peKelly.setPhrase(new Phrase("pe-middle-kelly"));
        pdfPTable.addCell(peKelly);

        PdfPHeaderCell pb=new PdfPHeaderCell();
        pb.setPhrase(new Phrase("pb"));
        pdfPTable.addCell(pb);
        PdfPHeaderCell pbLow=new PdfPHeaderCell();
        pbLow.setPhrase(new Phrase("pb-low"));
        pdfPTable.addCell(pbLow);
        PdfPHeaderCell pbMedian=new PdfPHeaderCell();
        pbMedian.setPhrase(new Phrase("pb-median"));
        pdfPTable.addCell(pbMedian);
        PdfPHeaderCell pbHigh=new PdfPHeaderCell();
        pbHigh.setPhrase(new Phrase("pb-high"));
        pdfPTable.addCell(pbHigh);
        PdfPHeaderCell pbKelly=new PdfPHeaderCell();
        pbKelly.setPhrase(new Phrase("pb-middle-kelly"));
        pdfPTable.addCell(pbKelly);

        return pdfPTable;

    }

    public static void writeChartToPDF(JFreeChart chart, int width, int height, String fileName) {
        PdfWriter writer = null;

        Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    fileName));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = new PdfGraphics2D(contentByte, width, height);
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                    height);

            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }

    public static void writeChartToPDF(JFreeChart chart, int width, int height, int x, int y, PdfWriter writer) {

        try {
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = new PdfGraphics2D(contentByte, width, height);
            Rectangle2D rectangle2d = new Rectangle2D.Double(x, y, width,
                    height);

            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, x, y);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ParseException, TushareException, IOException, DocumentException {
        PdfWriter writer = null;

        Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(
                    "C:/work/50chart.pdf"));
            document.open();
            PdfPTable pdfPTable = fullFillUqerIndexTable("");
            document.add(pdfPTable);
            document.newPage();

            document.add(new Paragraph("50"));
            writeChartToPDF(DefaultChartGenerator.generateIndexBasicVsShiborChart("000016.SH",
                    new SimpleDateFormat("yyyyMMdd").parse("20070101"),
                    new SimpleDateFormat("yyyyMMdd").parse("20200204"),
                    IndexDailyBasic.PE_TTM, IntrestRateFields.ONE_YEAR, true),
                    600, 400, 0, 0, writer);
            document.newPage();
            document.add(new Paragraph("300"));
            writeChartToPDF(DefaultChartGenerator.generateIndexBasicVsBondChart("000300.SH",
                    new SimpleDateFormat("yyyyMMdd").parse("20070101"),
                    new SimpleDateFormat("yyyyMMdd").parse("20200204"),
                    IndexDailyBasic.PE_TTM, "C:\\work\\data\\bond\\10y.csv", true),
                    600, 400, 0,0,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}
