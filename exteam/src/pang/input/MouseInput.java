package pang.input;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 *
 * @author Fernando
 */
public class MouseInput extends MouseAdapter{
//	Coordenadas del cursor del ratón
	public static int X, Y;
//        Nos indica si el boton presionado es el izquierdo. Mousse Left Button
	public static boolean MLB;
//Presionamos el boton	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			MLB = true;
		}
	}
//Soltamos el boton presionado
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			MLB = false;
		}
	}
//Objeto boton presionado
	@Override
	public void mouseDragged(MouseEvent e) {
		X = e.getX();
		Y = e.getY();
	}
//POSICION DEL RATON
	@Override
	public void mouseMoved(MouseEvent e) {
		X = e.getX();
		Y = e.getY();
	}
	
    
}
