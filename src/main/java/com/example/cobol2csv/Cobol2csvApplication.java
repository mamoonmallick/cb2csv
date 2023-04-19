package com.example.cobol2csv;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.CobolIoProvider;
import net.sf.JRecord.Numeric.Convert;
import net.sf.JRecord.cbl2csv.Cobol2Csv;
import net.sf.JRecord.zExamples.cobol.toCsv.test.ExampleConstants;
import net.sf.JRecord.zExamples.cobol.toCsv.test.TestCobol2Csv02_2;
import net.sf.JRecord.zTest.Common.TstConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Cobol2csvApplication {

    private static final Logger logger = LoggerFactory.getLogger(Cobol2csvApplication.class);

    public static void main(String[] args2) {
        SpringApplication.run(Cobol2csvApplication.class, args2);

        //convert2Csv();
        readCobol();
    }

    static void convert2Csv(){
        //String inputFileName = Cobol2csvApplication.class.getResource("DTAR1000_Store_file_std.bin").getFile();
        //logger.info("Filename = {}", inputFileName);

        String inputFileName = "D:/Cobol2Csv/DTAR1000_Store_file_std.bin";
        //String cpyFilename = Cobol2csvApplication.class.getResource("DTAR1000.cbl").getFile();
        String cpyFilename = "D:/Cobol2Csv/DTAR1000.cbl";

        String[] args= {
                "-I", inputFileName,
                "-O", ExampleConstants.TEMP_DIR + "DTAR1000_Store_file_std_02.csv",
                "-C", cpyFilename,
                "-Q", "\"",               /* Quote           */
                "-FS", "Mainframe_VB",    /* File Structure  */
                "-IC", "CP273",           /* Character set   */
                "-D", ";"                 /* Field Seperator */
        }; /* Field Seperator will default to \t */

        Cobol2Csv.main(args);
        logger.info("Successfully converted");
    }

    static void readCobol(){
        //String vendorFile = TstConstants.SAMPLE_DIRECTORY + "Ams_VendorDownload_20041229.txt";
        String vendorFile = "sample_files\\Ams_VendorDownload_20041229.txt";
        //String copybookName = TstConstants.COBOL_DIRECTORY + "AmsVendor.cbl";
        String copybookName =  "sample_files\\AmsVendor.cbl";
        CobolIoProvider ioProvider = CobolIoProvider.getInstance();
        AbstractLine line;
        int lineNumber = 0;

        try {
            AbstractLineReader reader = ioProvider.getLineReader(
                    Constants.IO_TEXT_LINE, Convert.FMT_INTEL,
                    CopybookLoader.SPLIT_NONE, copybookName, vendorFile
            );

            System.out.println("  Vendor \t Name");
            System.out.println("  ===========================================");


            while ((line = reader.read()) != null) {
                lineNumber += 1;
                System.out.println(
                        "  " + line.getFieldValue("Vendor-Number").asString()
                                + "  \t " + line.getFieldValue("Vendor-Name").asString());

            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error Line " + lineNumber + " " + e.getMessage());
        }
    }

}
