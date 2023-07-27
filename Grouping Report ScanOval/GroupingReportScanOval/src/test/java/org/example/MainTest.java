package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MainTest {

    @Test
    public void getKeyWithoutPrefixTest() {
        Assertions.assertEquals("microsoft:paint_3d", Main.getKeyWithoutPrefix("cpe:/a:microsoft:paint_3d"));
        Assertions.assertEquals("microsoft:windows_10", Main.getKeyWithoutPrefix("cpe:/o:microsoft:windows_10"));
    }

    @Test
    public void getKeysFromTextTest() {
        Assertions.assertEquals(Arrays.asList("microsoft:excel:2013", "microsoft:word:2013"),
                Main.getKeysFromText("microsoft:excel:2013 microsoft:word:2013"));
        Assertions.assertEquals(Arrays.asList("microsoft:excel:2013", "microsoft:office:2013",
                        "microsoft:outlook:2013", "microsoft:powerpoint:2013", "microsoft:word:2013"),
                Main.getKeysFromText("microsoft:excel:2013 microsoft:powerpoint:2013 microsoft:word:2013 " +
                        "microsoft:office:2013 microsoft:outlook:2013")
                );

    }

    @Test
    public void getDescriptionsProgramsTest() {
        Assertions.assertEquals(Arrays.asList(
                "C:\\Program Files (x86)\\Microsoft Office\\Office15\\EXCEL.EXE (15.0.4569.1504)",
                        "C:\\Program Files (x86)\\Microsoft Office\\Office15\\WINWORD.EXE (15.0.4569.1504)"),
                Main.getDescriptionsPrograms("C:\\Program Files (x86)\\Microsoft Office\\Office15\\EXCEL.EXE (15.0.4569.1504) " +
                        "C:\\Program Files (x86)\\Microsoft Office\\Office15\\WINWORD.EXE (15.0.4569.1504)"));
        Assertions.assertEquals(Arrays.asList(
                "C:\\Program Files (x86)\\Microsoft Office\\Office15\\EXCEL.EXE (15.0.4569.1504)",
                        "C:\\Program Files (x86)\\Microsoft Office\\Office15\\WINWORD.EXE (15.0.4569.1504)"),
                Main.getDescriptionsPrograms("C:\\Program Files (x86)\\Microsoft Office\\Office15\\WINWORD.EXE (15.0" +
                        ".4569.1504) C:\\Program Files (x86)\\Microsoft Office\\Office15\\EXCEL.EXE (15.0.4569.1504)"));
        Assertions.assertEquals(Arrays.asList(""), Main.getDescriptionsPrograms(""));

    }
}
