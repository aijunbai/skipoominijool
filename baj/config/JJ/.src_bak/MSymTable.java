package edu.ustc.cs.compile.skipoominijool;

import java.util.*;
import edu.ustc.cs.compile.platform.interfaces.SymTable;

public class MSymTable implements SymTable {
	private Hashtable table;
	protected MSymTable prev = null;

	public MSymTable(MSymTable p) {
		table = new Hashtable();
		prev = p;
	}

	public void put(Symbol sym) {
		int key = sym.key().hashCode();
		table.put(key, sym);
	}

	public Symbol get(SymbolKey skey) {
		int key = skey.hashCode();
		for (MSymTable e = this; e != null; e = e.prev) {
			Symbol found = (Symbol)(e.table.get(key));
			if (found != null) {
				return found;
			}
		}
		return null;
	}
	    
	public Symbol curget(SymbolKey skey) {
		int key = skey.hashCode();
		Symbol found = (Symbol)(table.get(key));
		if (found != null) {
			return found;
		}
		return null;
	}

    public void dump() {
		Enumeration e = table.elements();
		while( e.hasMoreElements() ){
			System.out.println( e.nextElement() );
		}

		if (prev != null){
			prev.dump();
		}
    }
}

