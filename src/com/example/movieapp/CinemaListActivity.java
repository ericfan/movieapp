package com.example.movieapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

/**
 * Example of removing yourself from the history stack after forwarding to
 * another activity.
 */
public class CinemaListActivity extends ListActivity {
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HashMap<String, String> item1 = new HashMap<String, String>();
		item1.put("line1", "�½ֿڵ�");
		item1.put("line2", "����������·�뻴��·���㴦");
		list.add(item1);
		HashMap<String, String> item2 = new HashMap<String, String>();
		item2.put("line1", "��۵�");
		item2.put("line2", "�¹�������·300�Ŵ�����3¥CY3-10����");
		list.add(item2);
		HashMap<String, String> item3 = new HashMap<String, String>();
		item3.put("line1", "������");
		item3.put("line2", "������������·341�����빫԰B1¥(���ָ������)");
		list.add(item3);
		ListAdapter adapter = new SimpleAdapter(this, list,
				android.R.layout.simple_list_item_2, new String[] { "line1",
						"line2" }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		setListAdapter(adapter);
	}
}
