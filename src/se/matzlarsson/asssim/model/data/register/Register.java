package se.matzlarsson.asssim.model.data.register;

import se.matzlarsson.asssim.model.data.AssByte;

public interface Register {

	public String getName();
	public int getByteSize();
	public AssByte getByte();
	public AssByte[] getBytes();
	public void setBytes(AssByte... data);
	public void setValue(int value);
	public void setValue(String value);
	
	public boolean add(int num);
	
}
