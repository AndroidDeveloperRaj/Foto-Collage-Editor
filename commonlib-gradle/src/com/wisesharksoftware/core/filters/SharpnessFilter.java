package com.wisesharksoftware.core.filters;

import android.graphics.Bitmap;

import com.wisesharksoftware.core.Filter;
import com.wisesharksoftware.core.FilterFactory;

import java.util.Map.Entry;

public class SharpnessFilter extends Filter
{
    private static final long serialVersionUID = 1L;
    
    private int size = 0;
    
    public SharpnessFilter() {
    	filterName = FilterFactory.SHARPNESS_FILTER;
    }
    
    public void setSize(int size) {
    	this.size = size;
    }
    
    @Override
    protected void onSetParams()
    {
        for( Entry<String, String> paramsEntry : params.entrySet() )
        {
            String name = paramsEntry.getKey();
            String value = paramsEntry.getValue();
            
            if (name.equals("size") )
            {
                size = Integer.parseInt(value);
            }           
        }
    }

    @Override
    public boolean hasNativeProcessing()
    {
        return false;
    }
/*
    @Override
    public boolean processOpenCV(Context context, String srcPath, String outPath) {
    	return false;
    	return hsvFilterOpenCV(srcPath, outPath, hue, saturation, value);
    }

    private static native boolean hsvFilterOpenCV(String inFileName, String outFileName, int hue, int saturation, int value);
*/
    
    @Deprecated
    private static native void nativeProcessing( Bitmap bitmap, int h, int s, int v );
    
    @Override
    public String convertToJSON() {
    	String s = "{";
    	s += "\"type\":" + "\"" + filterName + "\",";
    	//start params array
    	s += "\"params\":" + "[";
    	//param size
    	s += "{";
    	s += "\"name\":" + "\"" + "size" + "\",";
    	s += "\"value\":" + "\"" + size + "\"";
    	s += "}";
    	s += "]";
    	//end params array
    	s += "}";
    	return s;
    }
}
