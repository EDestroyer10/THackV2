package ubl.nohurtcam.events;

import java.util.ArrayList;
import java.util.EventListener;

public abstract class E<T extends EventListener> {
  public abstract void fire(ArrayList<T> paramArrayList);
  
  public abstract Class<T> getListenerType();
}
