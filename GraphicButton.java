/*
 *                    DO WHATEVER PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 * 0. You can do whatever you want to with the work.
 * 1. You cannot stop anybody from doing whatever they want to with the work.
 * 2. You cannot revoke anybody elses DO WHATEVER PUBLIC LICENSE in the work.
 *
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the DO WHATEVER PUBLIC LICENSE
 * 
 * Software originally created by Justin Lloyd @ http://otakunozoku.com/
 *
 */
import java.awt.* ;
import java.applet.*;
import java.lang.Runtime ;


/****************************************************************
*                                                               *
* NAME:	Button (class)											*
* Implements a two-state button class							*
*                                                               *
****************************************************************/

public class GraphicButton extends Object
	{
	boolean			bEnabled = true,
					bState = false,
					bLit = false,								// button is initially 'off'
					bTransparent = false;

	private	Image	imageButtonUp,									// image of unpressed button
					imageButtonDown,								// image of depressed button
					imageButtonLit = null;							// image of lit button

	public Image	imageTransSwap;
	public Graphics gTransSwap;

	private Applet	m_parent;										// Parent applet

	boolean bShowing = true;									// weather the button is hidden or showing
	
	Image bufferImage = null;
	private Graphics bufferGraphics = null;							// Image and Graphics to hold the button image
	
	int		m_x = 0, 
			m_y = 0, 
			m_width = 0, 
			m_height = 0;								// dimensions of the object

	int		m_status_mask;						// Identifier for the parent
			
	public GraphicButton(int x, int y, Image imageUp, Image imageDown, Applet parent)
		{
		m_parent = parent;
		imageButtonUp = imageUp ;
		imageButtonDown = imageDown ;
		move(x, y) ;
		resize(imageUp.getWidth(null), imageUp.getHeight(null)) ;
//		paint(getGraphics()) ;
		}

	public GraphicButton(int x, int y, int width, int height, Image imageUp, Image imageDown, Applet parent)
		{
		m_parent = parent;
		imageButtonUp = imageUp ;
		imageButtonDown = imageDown ;
		reshape(x,y,width,height);
		}

	public GraphicButton(int x, int y, int width, int height, Image imageUp, Image imageDown, Image imageLit, 
				Applet parent, boolean bTrans)
		{
		m_parent = parent;
		imageButtonUp = imageUp ;
		imageButtonDown = imageDown ;
		imageButtonLit = imageLit ;
		reshape(x,y,width,height);
		bTransparent = bTrans;
		if(bTrans) {
			imageTransSwap = parent.createImage(width,height);
			gTransSwap = imageTransSwap.getGraphics();
			}
		}

	public GraphicButton(int x, int y, int width, int height, String strText, Color colorDark, 
									Color colorLit, Color colorText, Font fontText, 
									Applet parent)
		{
		m_parent = parent;
		imageButtonUp = parent.createImage(width, height);
		imageButtonDown = parent.createImage(width, height);
		imageButtonLit = parent.createImage(width, height);
		Graphics gUp = imageButtonUp.getGraphics();
		Graphics gDown = imageButtonDown.getGraphics();
		Graphics gLit = imageButtonLit.getGraphics();
		FontMetrics fm;
		int xindent, yindent;

		gUp.setColor(colorDark);
		gUp.fill3DRect(0,0,width, height, true);
		gUp.setColor(colorText);
		gUp.setFont(fontText);
		fm = gUp.getFontMetrics();
		xindent = (width - fm.stringWidth(strText)) / 2;
		yindent = (height - fm.getHeight()) / 2 + fm.getMaxAscent(); 
		gUp.drawString(strText, xindent, yindent);

		gDown.setColor(colorLit);
		gDown.fill3DRect(0,0,width, height, false);
		gDown.setColor(colorText);
		gDown.setFont(fontText);
		gDown.drawString(strText, xindent, yindent);

		gLit.setColor(colorLit);
		gLit.fill3DRect(0,0,width, height, true);
		gLit.setColor(colorText);
		gLit.setFont(fontText);
		gLit.drawString(strText, xindent, yindent);
		reshape(x,y,width,height);
		}


	public GraphicButton(Image imageUp, Image imageDown, Applet parent)
		{
		imageButtonUp = imageUp ;
		imageButtonDown = imageDown ;
		resize(imageUp.getWidth(null), imageUp.getHeight(null)) ;
//		paint(getGraphics()) ;
		}
	
	
	public void action()
		{
		}


	public void paint()
		{
		paint(getGraphics()) ;
		}


    public void paint(Graphics graphics)
		{
		if (graphics != null)
			{
			if (bState)
				graphics.drawImage(imageButtonDown, 0, 0, null) ;
			else
				if(bLit) 
					graphics.drawImage(imageButtonLit, 0, 0, null) ;
				else
					graphics.drawImage(imageButtonUp, 0, 0, null) ;

			}


		}


	public boolean mouseDown(Event event, int x, int y)
		{
		if (bEnabled)
			{
			Down() ;
			return(true) ;
			}

		return (false) ;
		}


	public boolean mouseUp(Event event, int x, int y)
		{
		if (bEnabled)
			{
			Up() ;
//			if (inside(x, y))
//				{
//				event.id = Event.ACTION_EVENT ;
//				event.arg = (Object)this ;
//
//				return (action(event, event.arg)) ;
//				}
			return true;		
			}

		return(false) ;
		}


  public boolean Flip()
		{
		bState = !bState ;											// flip state of button
		paint(getGraphics()) ;										// repaint button

		return (bState) ;											// return the new state
		}


	public boolean Up()
		{
		boolean	bPreviousState = bState ;							// save previous state

		bState = false ;											// set state 'off'
		paint(getGraphics()) ;										// repaint button
		m_parent.repaint();
		return (bPreviousState) ;									// return previous state
		}


	public boolean Down()
		{
		boolean	bPreviousState = bState ;							// save previous state

		bState = true ;												// set state 'on'
		paint(getGraphics()) ;										// repaint button
		m_parent.repaint();
		return (bPreviousState) ;									// return previous state
		}

	
	public boolean State()
		{
		return (bState) ;											// return current state
		}


	public Image Image()
		{
		return (bState ? imageButtonDown : imageButtonUp) ;			// return image displayed
		}

	public void disable()
		{
		bEnabled = false ;
		}

	public void enable()
		{
		bEnabled = true ;
		}

	public void light()
		{
		bLit = true ;
		}

	public void unlight()
		{
		bLit = false ;
		}

	public void reshape(int x, int y, int width, int height) {
		m_x = x;
		m_y = y;
		m_width = width;
		m_height = height;
		if(bufferImage != null) {
			bufferImage.flush();
			bufferGraphics.dispose();
			}
		bufferImage = m_parent.createImage(width,height);
		bufferGraphics = bufferImage.getGraphics();
		repaint();
		}

	public void move(int x, int y) {
		m_x = x;
		m_y = y;
		repaint();
		}
	
	public void resize(int width, int height) {
		m_width = width;
		m_height = height;
		if(bufferImage != null) {
			bufferImage.flush();
			bufferGraphics.dispose();
			}
		bufferImage = m_parent.createImage(width,height);
		bufferGraphics = bufferImage.getGraphics();
		repaint();
		}

	public void repaint() {
		paint(bufferGraphics);
//		m_parent.repaint();
		}

	Graphics getGraphics() {
		return bufferGraphics;
		}
	
	public Image getImage() {
		if(bShowing) {
			if(bState) return imageButtonDown;
			if(bLit) return imageButtonLit;
			return imageButtonUp;
			}
		return null;
		}

	public void show() {
		bShowing = true;
		repaint();
		}

	public void hide() {
		bShowing = false;
		repaint();
		}

	public boolean inside(int x, int y) {
		if(!bShowing) return false;
		if(new Rectangle(m_x, m_y, m_width, m_height).inside(x,y)) return true;
		return false;
		}

	}
