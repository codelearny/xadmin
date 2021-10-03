package com.enjoyu.admin.integration.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EasyExcelTest {
    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
    @Test
    public void simpleRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
    }

    @Test
    public void multiSheetRead() {
        ExcelReader reader = null;
        try {
            String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
            reader = EasyExcel.read(fileName).build();
            DemoDataListener listener0 = new DemoDataListener();
            DemoDataListener listener1 = new DemoDataListener();
            ReadSheet sheet0 = EasyExcel.readSheet("sheet0").headRowNumber(3).head(DemoData.class).registerReadListener(listener0).build();
            ReadSheet sheet1 = EasyExcel.readSheet("sheet1").headRowNumber(3).head(DemoData.class).registerReadListener(listener1).build();

            reader.read(sheet0, sheet1);
            System.out.println(listener0.list);
            System.out.println(listener1.list);

        } finally {
            if (reader != null) {
                reader.finish();
            }
        }
    }

    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        String fileName = TestFileUtil.getPath() + "write" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
    }


    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}