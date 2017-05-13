package test.state_machine.serialize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum HazelcastManagement {

  INSTANCE("DEFAULT_PERSIST_MAP");

  private Map<String, Map<String, byte[]>> cache = new ConcurrentHashMap<>();
  private String mapName;
  
  private HazelcastManagement(String mapName) {
    this.mapName = mapName;
  }
  
  public void setMapName(String mapName) {
    this.mapName = mapName;
  }
  
  public byte[] persist(String key, byte[] value) {
    byte[] res = null;
    if (cache.containsKey(mapName)) {
      Map<String, byte[]> metaData = cache.get(mapName);
      res = metaData.put(key, value);
    } else {
      Map<String, byte[]> metaData = new ConcurrentHashMap<>();
      res = metaData.put(key, value);
      cache.put(mapName, metaData);
    }
    return res;
  }

  public byte[] getMetaData(String key) {
    byte[] res = null;
    if (cache.containsKey(mapName)) {
      Map<String, byte[]> metaData = cache.get(mapName);
      res = metaData.get(key);
    }
    return res;
  }

}
