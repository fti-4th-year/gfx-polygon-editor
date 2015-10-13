package FF_12312_Gerasev_Span;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class FileLoader {
	private static String prepareLine(String line) {
		return line.split("//")[0].trim().replaceAll("\\s+", " ");
	}
	
	private static Polygon readPolygon(BufferedReader br) throws Exception {
		Polygon polygon = null;
		String line;
		int size = 0;
		
		while((line = br.readLine()) != null) {
			line = prepareLine(line);
			if(line.isEmpty()) {
				continue;
			}
			
			if(polygon == null) {
				String[] array = line.split(" ");
				if(array.length >= 2) {
					int width;
					try {
						size = Integer.parseInt(array[0]);
						width = Integer.parseInt(array[1]);
					} catch (NumberFormatException nfe) {
						throw new Exception("polygon {vertex_count, line_width} parse error");
					}
					polygon = new Polygon();
					polygon.setWidth(width);
				} else {
					throw new Exception("too few words in line");
				}
			} else {
				String[] array = line.split(" ");
				if(array.length >= 2) {
					int x, y;
					try {
						x = Integer.parseInt(array[0]);
						y = Integer.parseInt(array[1]);
					} catch (NumberFormatException nfe) {
						throw new Exception("polygon coords parse error");
					}
					polygon.add(new Point(x, y));
					if(polygon.getSize() >= size) {
						break;
					}
				} else {
					throw new Exception("too few words in line");
				}
			}
		}
		
		if(polygon == null) {
			throw new Exception("unexpected end of file");
		}
		
		return polygon;
	}
	
	private static Fill readFill(BufferedReader br) throws Exception {
		Fill fill = null;
		String line;
		
		while((line = br.readLine()) != null) {
			line = prepareLine(line);
			if(line.isEmpty()) {
				continue;
			}
			
			String[] array = line.split(" ");
			if(array.length >= 4) {
				int x, y, color, type;
				try {
					x = Integer.parseInt(array[0]);
					y = Integer.parseInt(array[1]);
					color = Integer.parseInt(array[2]);
					type = Integer.parseInt(array[3]);
				} catch (NumberFormatException nfe) {
					throw new Exception("fill {x, y, color, type} parse error");
				}
				if(type != 4 && type != 8) {
					throw new Exception("unknown fill type '" + type + "'");
				}
				fill = new Fill(new Point(x, y), color == 0 ? Fill.WHITE : Fill.DEFAULT);
				break;
			} else {
				throw new Exception("too few words in line");
			}
		}
		
		if(fill == null) {
			throw new Exception("unexpected end of file");
		}
		
		return fill;
	}
	
	public static Storage load(String path) throws Exception {
		Storage storage = new Storage();
		
		InputStream fis = new FileInputStream(path);
		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		
		try {
			String line;
			
			while((line = br.readLine()) != null) {
				line = prepareLine(line);
				if(line.isEmpty()) {
					continue;
				}
				
				if(line.compareToIgnoreCase("POLYGON") == 0) {
					Polygon p = readPolygon(br);
					if(p != null) {
						storage.add(p);
					}
				} else if(line.compareToIgnoreCase("FILL") == 0) {
					Fill f = readFill(br);
					if(f != null) {
						storage.add(f);
					}
				} else {
					throw new Exception("unknown keyword '" + line + "'");
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
			ArrayList<Object> elements = storage.getElements();
			for(int i = 0; i < elements.size(); ++i) {
				if(elements.get(i) instanceof Polygon) {
					writePolygon(writer, (Polygon) elements.get(i));
				} else if(elements.get(i) instanceof Fill) {
					writeFill(writer, (Fill) elements.get(i));
				}
			}
		} finally {
			writer.close();
		}
	}
	
	private static void writePolygon(PrintWriter writer, Polygon polygon) {
		writer.println("POLYGON");
		writer.println(polygon.getSize() + " " + polygon.getWidth());
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
	
	private static void writeFill(PrintWriter writer, Fill fill) {
		writer.println("FILL");
		writer.println(
				fill.getSeed().x + " " + fill.getSeed().y + " " +
				((fill.getColor() == 0xffffff) ? 0 : 1) + " " + 4
				);
		writer.println();
	}
}
