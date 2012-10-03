package com.dds;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel.TextAlignment;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.opengl.GLResourceHelper;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

public class Label extends CCSprite
{
	private CGSize _dimensions;
	private TextAlignment _alignment;
	private String _fontName;
	private float _fontSize;
	private String _string;
	private CCTexture2D _texture;
	
	public static Label makeLabel(String string, CGSize dimensions, TextAlignment alignment, String fontname, float fontsize)
	{
	    return new Label(string, dimensions, alignment, fontname, fontsize);
	}
	
	public static Label makeLabel(String string, String fontname, float fontsize)
	{
	    return new Label(string, CGSize.make(0.0F, 0.0F), TextAlignment.CENTER, fontname, fontsize);
	}
	
	protected Label(CharSequence string, String fontname, float fontsize)
	{
	    this(string, CGSize.make(0.0F, 0.0F), TextAlignment.CENTER, fontname, fontsize);
	}
	
	protected Label(CharSequence string, CGSize dimensions, TextAlignment alignment, String name, float size)
	{
	    _dimensions = dimensions;
	    _alignment = alignment;
	    _fontName = name;
	    _fontSize = size;
	    setString(string);
	}
	
	public void setString(CharSequence seq)
	{
	    if(_string != null && _string.equals(seq))
	    {
	        return;
	    }
	    else
	    {
	        String string = seq.toString();
	        _string = string;
	        
	        if (_texture != null)
	        {
	        	_texture.releaseTexture(CCDirector.gl);
	        }
	        else
	        {
	        	_texture = new CCTexture2D();
	        	setTexture(_texture);
	        }
	        
	        _texture.setLoader(new GLResourceHelper.GLResourceLoader() 
	        {
	    		public void load(GLResourceHelper.Resource res) 
	    		{
	    	    	if (CGSize.equalToSize(_dimensions, CGSize.zero())) 
	    	    	{
	    	    		((CCTexture2D)res).initWithText(_string, _fontName, _fontSize);
	    	    	} 
	    	    	else 
	    	    	{
	    	    		((CCTexture2D)res).initWithText(_string, _dimensions, _alignment, _fontName, _fontSize);
	    	    	}

	    		    CGSize size = getTexture().getContentSize();
	    		    setTextureRect(CGRect.make(0, 0, size.width, size.height));
	    		}
	    	});
	        
	        setTexture(_texture);
	        
	        return;
	    }
	}
}