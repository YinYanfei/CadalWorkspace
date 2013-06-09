package cn.cadal.parseMetadata;

import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.cadal.util.StackTraceUtil;

/**
 * sax解析器的Handler，用于解析Catalog.xml
 * 
 * @author zhangpeng
 * 
 */
public class ParseCatalog extends DefaultHandler {
	static private final Log LOG = LogFactory.getLog(ParseCatalog.class);

	SAXParser parser;
	SAXParserFactory saxfactory;
	// 类创建时执行
	{
		saxfactory = SAXParserFactory.newInstance();
		saxfactory.setNamespaceAware(false);
		saxfactory.setValidating(false);
		try {
			parser = saxfactory.newSAXParser();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}
	}

	//
	Stack<Record> level0 = new Stack<Record>();
	Stack<Record> level1 = new Stack<Record>();
	Stack<Record> level2 = new Stack<Record>();
	Stack<Record> level3 = new Stack<Record>();
	Stack<Record> level4 = new Stack<Record>();

	String label = null; // 当前章节的名称
	int curLevel = -1; // 当前章节的深度
	int[] levels = { 0, 0, 0, 0, 0 }; // 代表当前目录层次，例如{1,2,3,4,5}表示第1.2.3.4.5节
	Record[] record = new Record[5]; // Record类自建，表示一条要插入到数据库中的纪录，包括字段BookId,ChapterLevel,ChapterLabel,StartPage
	public String bookId;
	public int endPage;

	// 每解析完一本书时需要将层次信息清零
	public void resetLevels() {
		for (int i = 0; i < 5; i++) {
			levels[i] = 0;
		}
		curLevel = -1;
		record=new Record[5];
		level0.clear();
		level1.clear();
		level2.clear();
		level3.clear();
		level4.clear();
	}

	/**
	 * 虽然每次成功解析完一本书时 curLevel 会恢复到-1, 添加这个函数的目的是:在解析某本书出错,即没有成功地
	 * 解析完当前这本书时,跳过这本,继续解析下一本时,curLevel会保留前一次的记录
	 */
	public void resetCurLevel() {
		curLevel = -1;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		int pageStart = -1;
		if (qName.endsWith("div")) {
			curLevel++;
			levels[curLevel]++;

			int attrLength = attributes.getLength();
			for (int index = 0; index < attrLength; index++) {
				String attrQName = attributes.getQName(index);
				String attrValue = attributes.getValue(index);
				if (attrQName.equals("LABEL")) {
					label = attrValue;
					break;
				}
			}
		} else if (qName.endsWith("fptr")) {

			int attrLength = attributes.getLength();
			for (int index = 0; index < attrLength; index++) {
				String attrQName = attributes.getQName(index);
				String attrValue = attributes.getValue(index);
				if (attrQName.equals("FILEID")) {
					pageStart = Integer.parseInt(attrValue.trim());
					break;
				}
			}

			switch (curLevel) {
			case 0:
				if (!level0.isEmpty()) {
					Record record = level0.pop();
					int endPage = pageStart - 1;
					record.setEndPage(endPage);
				//	System.out.println(record.toString());
					record.insertToDB();
				}
				level0.push(new Record(bookId, levels[0] + "." + levels[1]
						+ "." + levels[2] + "." + levels[3] + "." + levels[4],
						label, pageStart, -1));
				break;
			case 1:
				if (!level1.isEmpty()) {
					Record record = level1.pop();
					int endPage = pageStart - 1;
					record.setEndPage(endPage);
				//	System.out.println(record.toString());
					record.insertToDB();
				}
				level1.push(new Record(bookId, levels[0] + "." + levels[1]
						+ "." + levels[2] + "." + levels[3] + "." + levels[4],
						label, pageStart, -1));
				break;
			case 2:
				if (!level2.isEmpty()) {
					Record record = level2.pop();
					int endPage = pageStart - 1;
					record.setEndPage(endPage);
				//	System.out.println(record.toString());
					record.insertToDB();
				}
				level2.push(new Record(bookId, levels[0] + "." + levels[1]
						+ "." + levels[2] + "." + levels[3] + "." + levels[4],
						label, pageStart, -1));
				break;
			case 3:
				if (!level3.isEmpty()) {
					Record record = level3.pop();
					int endPage = pageStart - 1;
					record.setEndPage(endPage);
					//System.out.println(record.toString());
					record.insertToDB();
				}
				level3.push(new Record(bookId, levels[0] + "." + levels[1]
						+ "." + levels[2] + "." + levels[3] + "." + levels[4],
						label, pageStart, -1));
				break;
			case 4:
				if (!level4.isEmpty()) {
					Record record = level4.pop();
					int endPage = pageStart - 1;
					record.setEndPage(endPage);
					//System.out.println(record.toString());
					record.insertToDB();
				}
				level4.push(new Record(bookId, levels[0] + "." + levels[1]
						+ "." + levels[2] + "." + levels[3] + "." + levels[4],
						label, pageStart, -1));
				break;
			}
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.endsWith("div")) {
			if (curLevel != 4) {
				levels[curLevel + 1] = 0;
			}
			curLevel--;
		}
	}

	public void endDocument() {
		if (!level0.isEmpty()) {
			Record record = level0.pop();
			record.setEndPage(endPage);
			//System.out.println(record.toString());
			record.insertToDB();
		}
		if (!level1.isEmpty()) {
			Record record = level1.pop();
			record.setEndPage(endPage);
			//System.out.println(record.toString());
			record.insertToDB();
		}
		if (!level2.isEmpty()) {
			Record record = level2.pop();
			record.setEndPage(endPage);
			//System.out.println(record.toString());
			record.insertToDB();
		}
		if (!level3.isEmpty()) {
			Record record = level3.pop();
			record.setEndPage(endPage);
			//System.out.println(record.toString());
			record.insertToDB();
		}
		if (!level4.isEmpty()) {
			Record record = level4.pop();
			record.setEndPage(endPage);
			//System.out.println(record.toString());
			record.insertToDB();
		}
	}

	// 执行解析操作
	public boolean doParse(InputStream in) {
		boolean flag = true;
		try {
			parser.parse(in, this);
		} catch (Exception e1) {
			flag = false;
			LOG.warn(StackTraceUtil.getStackTrace(e1));
		} finally {
			return flag;
		}
	}
}
