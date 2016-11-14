package com.ows.mylaucher;

import org.litepal.crud.DataSupport;

public class Contactbean extends DataSupport implements Comparable<Contactbean>{
	public String name;
	public String number;
	public int group;
	public int rank;
	@Override
	public int compareTo(Contactbean another) {
		int init = 0;
		if (this.group>another.group) {
			return 1;
			}
		else if (this.group<another.group) {
			return -1;
			
		}
		else {
			if (this.rank>another.rank) {
				return 1;
				
			}
			else {
				return -1;
			}
			
		}
		
	}
	@Override
	public String toString() {
		return "" + name + "," + number + ","+ group + "," + rank+"\n";
	}	
	
} 