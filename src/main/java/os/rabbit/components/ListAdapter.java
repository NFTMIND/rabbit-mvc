package os.rabbit.components;

import java.io.PrintWriter;
import java.util.List;

import os.rabbit.components.ComponentList;
import os.rabbit.components.IListListener;
import os.rabbit.parser.Tag;

public class ListAdapter<T> implements IListListener<T> {

	public ListAdapter() {

	}

	@Override
	public void beforeEachRender(PrintWriter writer, T object, int index) {

	}

	@Override
	public void afterEachRender(PrintWriter writer, T object, int index) {

	}

	@Override
	public void emptyRender(PrintWriter writer) {

	}

}
