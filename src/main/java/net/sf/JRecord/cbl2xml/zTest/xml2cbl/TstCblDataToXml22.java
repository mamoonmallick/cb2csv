/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: JRecord Cbl2Xml
 *    
 *    Sub-Project purpose: Convert Cobol Data files to / from Xml
 *
 *                 Author: Bruce Martin
 *    
 *                License: LGPL 2.1 or latter
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 * ------------------------------------------------------------------------ */

package net.sf.JRecord.cbl2xml.zTest.xml2cbl;

import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.xml.sax.SAXException;

import net.sf.JRecord.Common.IFileStructureConstants;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.Option.IReformatFieldNames;
import net.sf.JRecord.cbl2xml.impl.Cobol2GroupXml;

public class TstCblDataToXml22 {

	private String[][] files ={
			{"FCUSDAT.cbl",  "ZOS.FCUSTDAT_150.vb.bin", "ZOS.FCUSTDAT_150.xml","N", "V"},
			{"DTAR020.cbl",  "DTAR020.bin",  "DTAR020_DataN.xml", "N", "F"},
			{"DTAR107.cbl",  "DTAR107.bin",  "DTAR107_DataN.xml", "N", "F"},
			{"DTAR192.cbl",  "DTAR192.bin",  "DTAR192_DataN.xml", "N", "F"},
			{"DTAR1000.cbl", "DTAR1000.bin", "DTAR1000_DataN.xml","N", "V"},
			{"FCUSDAT.cbl",  "ZOS.FCUSTDAT_150.vb.bin", "ZOS.FCUSTDAT_150.xml","N", "V"},
			{"DTAR020.cbl",  "DTAR020.bin",  "DTAR020_DataY.xml", "Y", "F"},
			{"DTAR107.cbl",  "DTAR107.bin",  "DTAR107_DataY.xml", "Y", "F"},
			{"DTAR192.cbl",  "DTAR192.bin",  "DTAR192_DataY.xml", "Y", "F"},
			{"DTAR1000.cbl", "DTAR1000.bin", "DTAR1000_DataY.xml","Y", "V"},
	};
	private static final int[] TAG_FORMATS = {
		IReformatFieldNames.RO_LEAVE_ASIS, IReformatFieldNames.RO_UNDERSCORE, IReformatFieldNames.RO_CAMEL_CASE
	};


	private int tagFormat;

	//private String firstLine = "TAR5839DCDC - Taras Ave                                                             30-68 Taras Ave                         Altona North                       3025      VICA";
	@Test
	public void testData2Xml() throws IOException, SAXException, ParserConfigurationException, RecordException, XMLStreamException {
		for (String[] d : files) {
			check(d);
		}
	}
	
	public void testData2Xml_DTAR107() throws IOException, SAXException, ParserConfigurationException, RecordException, XMLStreamException {
		check(files[4]);
	}

	/**
	 * @param d
	 * @throws FileNotFoundException
	 * @throws RecordException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws XMLStreamException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws FactoryConfigurationError
	 */
	private void check(String[] d) throws FileNotFoundException,
			RecordException, IOException, XMLStreamException,
			SAXException, ParserConfigurationException,
			FactoryConfigurationError {
		String copybookName;
		String dataName;
//		String xmlDataName;
		String xmlData = Cb2XmlCode.loadFile(Cb2XmlCode.getFullName("xml/" + d[2]), "\r\n", false);
		
		for (int tf : TAG_FORMATS) {
			tagFormat = tf;
			System.out.println("Checking: " + d[0] + ", " + tf);
			copybookName = Cb2XmlCode.getFullName("cobol/" + d[0]);
			dataName = Cb2XmlCode.getFullName(d[1]);
			
			byte[] doc = data2xml(dataName, copybookName, "Y".equalsIgnoreCase(d[3]),  "V".equalsIgnoreCase(d[4]));
			
			
			//System.out.println(XmlUtils.domToString(doc));
			System.out.println("Copybook: " + d[0] + " " + d[2]);
			System.out.println();
			System.out.println(new String(doc));
		
//		xmlDataName = Cb2XmlCode.getFullName("xml/" + d[2]);
//		Cb2XmlCode.compare("File: " + copybookName,  xmlDataName, doc);
			Cb2XmlCode.compareXmlStr("File: " + tf + ", " + copybookName,  ReformatXml.reformaXml(tf, xmlData), doc);
		}
	} 
	
	private byte[] data2xml(String dataFileName, String copybookFileName, boolean dropCopybookName, boolean vb) 
	throws FileNotFoundException, RecordException, IOException, XMLStreamException, SAXException, ParserConfigurationException {
		
		ByteArrayOutputStream os = new ByteArrayOutputStream(0x10000);
		int fileOrg = IFileStructureConstants.IO_FIXED_LENGTH;
		if (vb) {
			fileOrg = IFileStructureConstants.IO_VB;
		}
		
		Cobol2GroupXml.newCobol2Xml(copybookFileName)
					  .setFileOrganization(fileOrg)
					  .setXmlMainElement("copybook")
					  .setFont("cp037")
					  .setDialect(ICopybookDialects.FMT_MAINFRAME) // This is the default
					  .setTagFormat(tagFormat)
					  .setDropCopybookNameFromFields(dropCopybookName)
					  .cobol2xml(new FileInputStream(dataFileName), os);

	    return os.toByteArray();
	}
	
	
	@Test
	public void testXml2Data() throws IOException, SAXException, ParserConfigurationException, RecordException, XMLStreamException {
		byte[] xml2data;
		int size = files.length / 2;
		byte[][] expected = new byte[size][];
		String[] d = files[0];
		
		for (int i = 0; i < expected.length; i++) {
			expected[i] = readFile(Cb2XmlCode.getFullName(files[i][1]));
		}
		for (int i = 0; i < files.length; i++) { // xml2data only works when there are no arrays !!!
			d = files[i];
			if (d[1].indexOf("DTAR107") < 0) {
				String xmlStr = Cb2XmlCode.loadFile(Cb2XmlCode.getFullName("xml/" + d[2]), "\r\n", false);
				for (int tf : TAG_FORMATS) {
					tagFormat = tf;
					try {
	
						System.out.println("->> " + d[0] + " " + d[2]);
						xml2data = xml2data(
								new ByteArrayInputStream(
									ReformatXml.reformaXml(tagFormat, xmlStr).getBytes()	
								), 
								Cb2XmlCode.getFullName("cobol/" + d[0]), 
								"Y".equalsIgnoreCase(d[3]),  "V".equalsIgnoreCase(d[4]));
						//System.out.println(xml2data);
						assertArrayEquals("idx=" + 1, expected[i%size], xml2data);
					} catch (RuntimeException e) {
						System.err.println();
						System.err.println("   --> " + tf + ", " + i + " " + d[0]  + " " + d[1] + " " + d[2]);
						System.err.println();
						throw e;
					} catch (Exception e) {
						System.err.println();
						System.err.println("   --> " + tf + ", " + i + " " + d[0]  + " " + d[1] + " " + d[2]);
						System.err.println(ReformatXml.reformaXml(tagFormat, xmlStr));
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	private byte[] xml2data(InputStream dataStream, String copybookFileName, boolean dropCopybookName, boolean vb) 
	throws FileNotFoundException, RecordException, IOException, XMLStreamException {
		
		ByteArrayOutputStream os = new ByteArrayOutputStream(0x10000);
		int fileOrg = IFileStructureConstants.IO_FIXED_LENGTH;
		if (vb) {
			fileOrg = IFileStructureConstants.IO_VB;
		}

		Cobol2GroupXml.newCobol2Xml(copybookFileName)
					  .setFileOrganization(fileOrg)
					  .setXmlMainElement("copybook")
					  .setFont("cp037")
					  .setDropCopybookNameFromFields(dropCopybookName)
					  .setTagFormat(tagFormat)
					  .xml2Cobol(dataStream, os);
		return os.toByteArray();

	}
	
	private byte[] readFile(String fileName) throws IOException {
		InputStream is = new FileInputStream(fileName);
		ByteArrayOutputStream os = new ByteArrayOutputStream(4000);
		byte[] buf = new byte[4000];
		
		int len = is.read(buf);
		while (len > 0) {
			os.write(buf, 0, len);
			len = is.read(buf);
		}
		is.close();
		
		return os.toByteArray();
		
		
	}

}
