package FF_12312_Gerasev_PG;

public class Scene {
	private boolean _dirty = false;
	private Storage _storage = null;
	private Polygon _polygon = null;
	private Line _line = null;
	
	public Storage getStorage() {
		return _storage;
	}
	
	public void setStorage(Storage storage) {
		_storage = storage;
	}
	
	public Polygon getPolygon() {
		return _polygon;
	}
	
	public void setPolygon(Polygon polygon) {
		_polygon = polygon;
	}
	
	public Line getLine() {
		return _line;
	}
	
	public void setLine(Line line) {
		_line = line;
	}
	
	public boolean getDirty() {
		return _dirty;
	}
	
	public void setDirty(boolean flag) {
		_dirty = flag;
	}
}
