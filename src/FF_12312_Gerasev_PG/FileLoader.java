package FF_12312_Gerasev_PG;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class FileLoader {
	private static boolean readToPolygonBegin(BufferedReader br) throws Exception {
		String line;
		while((line = br.readLine()) != null) {
			line = line.trim();
			if(line.isEmpty()) {
				continue;
			}
			
			if(line.compareToIgnoreCase("Polygon") == 0)
			{
				return true;
			}
			
			throw new Exception("unknown word");
		}
		return false;
	}
	
	private static Polygon readPolygon(BufferedReader br) throws Exception {
		Polygon polygon = null;
		String line;
		
		while((line = br.readLine()) != null) {
			if(polygon == null)
				polygon = new Polygon();
			
			line = line.trim();
			if(line.isEmpty()) {
				break;
			}
			
			if(line.compareToIgnoreCase("Polygon") == 0)
			{
				return polygon;
			}
			
			String[] array = line.split(" ");
			if(array.length >= 2) {
				int x, y;
				try {
					x = Integer.parseInt(array[0]);
					y = Integer.parseInt(array[1]);
				} catch (NumberFormatException nfe) {
					throw new Exception("coords parse error");
				}
				
				polygon.add(new Point(x, y));
				
			} else {
				throw new Exception("too few coords per line");
			}
		}
		
		return polygon;
	}
	
	public static Storage load(String path) throws Exception {
		Storage storage = new Storage();
		
		InputStream fis = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		
		try {
			String line;
			int pnum = 0;
			boolean empty = true;
			
			while((line = br.readLine()) != null) {
				empty = false;
				line = line.trim();
				if(line.isEmpty())
					continue;
				try {
					pnum = Integer.parseInt(line);
				}
				catch (NumberFormatException nfe) {
					throw new Exception("polygon number parse error");
				}
				break;
			}
			if(empty)
				throw new Exception("file is empty");
			
			if(pnum == 0) {
				return storage;
			}
			
			for(int i = 0; i < pnum; ++i) {
				if(readToPolygonBegin(br)) {	
					Polygon polygon = readPolygon(br);
					if(polygon != null) {
						storage.add(polygon);
					} else {
						if(i + 1 < pnum)
							throw new Exception("unexpected end of file");
					}
				} else {
					throw new Exception("unexpected end of file");
				}
			}
		} finally {
			br.close();
			isr.close();
			fis.close();
		}
		
		return storage;
	}
	
	public static void save(String path, Storage storage) throws Exception {
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		
		try {
			writer.println(storage.getSize());
			ArrayList<Polygon> polygons = storage.getPolygons();
			for(int i = 0; i < polygons.size(); ++i) {
				writePolygon(writer, polygons.get(i));
			}
		} finally {
			writer.close();
		}
	}
	
	private static void writePolygon(PrintWriter writer, Polygon polygon) {
		writer.println("Polygon");
		ArrayList<Point> vertices = polygon.getVertices();
		for(int i = 0; i < vertices.size(); ++i) {
			Point point = vertices.get(i);
			writer.print(point.x);
			writer.print(" ");
			writer.print(point.y);
			writer.println();
		}
		writer.println();
	}
}
