package edu.sjsu.hemepathcounter.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.hemepathcounter.model.CellButton.CellType;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable, JSONable {
	private HashMap<String, Integer> map;
	private int total;
	private String timestamp;
	private double MEratio;

	public Data(Counter counter) {
		// Fill the map with the names of the buttons, and their counts.
		map = new HashMap<String, Integer>();
		double Mcount = 0;
		double Ecount = 0;
		for (CellButton c : counter.getCells()) {
			map.put(c.getName(), c.getCount());
			if (c.getType() == CellType.MYELOID) {
				Mcount += c.getCount();
			} else if (c.getType() == CellType.ERYTHROID) {
				Ecount += c.getCount();
			}
		}

		// Format the timestamp and set the variable
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
				DateFormat.MEDIUM);
		timestamp = format.format(new Date());

		// Set the total
		total = 0;
		for (String key : map.keySet()) {
			total += map.get(key).intValue();
		}

		MEratio = Mcount / Ecount;

	}

	public Data() {
		map = new HashMap<String, Integer>();
		total = 0;
		MEratio = 0.0;
		timestamp = "";
	}

	public Data(Parcel in) {
		readFromParcel(in);
	}

	public HashMap<String, Integer> getMap() {
		return map;
	}

	public int getTotal() {
		return total;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public double getMEratio() {
		return MEratio;
	}
	/**
	 * Check if the M:E ratio is valid
	 * @return true if the M:E ratio is not infinity or NaN (not a number)
	 */
	public boolean isMEratioValid() {
		return !Double.isInfinite(MEratio) && !Double.isNaN(MEratio);
	}

	@Override
	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = new JSONObject();
		JSONArray keyArray = new JSONArray(map.keySet());
		JSONArray valueArray = new JSONArray();
		for (String key : map.keySet()) {
			valueArray.put(map.get(key));
		}
		jo.put("keyArray", keyArray);
		jo.put("valueArray", valueArray);
		jo.put("timestamp", timestamp);
		jo.put("MEration", MEratio);
		return jo;
	}

	@Override
	public void fromJSONObject(JSONObject src) throws JSONException {
		map = new HashMap<String, Integer>();
		JSONArray jsonKeys = src.getJSONArray("keyArray");
		JSONArray jsonValues = src.getJSONArray("valueArray");
		for (int i = 0; i < jsonKeys.length(); i++) {
			map.put((String) jsonKeys.get(i), (Integer) jsonValues.get(i));
		}
		timestamp = src.getString("timestamp");
		total = 0;
		for (String key : map.keySet()) {
			total += map.get(key).intValue();
		}
		MEratio = src.getDouble("MEratio");
	}

	private void readFromParcel(Parcel in) {
		map = new HashMap<String, Integer>();
		Bundle temp = in.readBundle();
		for (String key : temp.keySet()) {
			map.put(key, (Integer) temp.get(key));
		}
		total = in.readInt();
		timestamp = in.readString();
		MEratio = in.readDouble();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle b = new Bundle();
		for (Entry<String, Integer> e : map.entrySet()) {
			b.putInt(e.getKey(), e.getValue());
		}
		dest.writeBundle(b);
		dest.writeInt(total);
		dest.writeString(timestamp);
		dest.writeDouble(MEratio);
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Data createFromParcel(Parcel in) {
			return new Data(in);
		}

		public Data[] newArray(int size) {
			return new Data[size];
		}
	};

	public String toString() {
		return timestamp;
	}

	public String getCSVasString() {
		DecimalFormat df = new DecimalFormat("#.##");
		String csv = "";
		csv += "Name";
		csv += ',';
		csv += "Count";
		csv += ',';
		csv += "Percentage";
		csv += '\n';

		for (String key : map.keySet()) {
			csv += key;
			csv += ',';
			csv += "" + map.get(key);
			csv += ',';
			csv += "" + String.format(df.format(100.0 * map.get(key) / total))
					+ "%";
			csv += '\n';
		}
		
		//include MEratio only if it applies
		if (isMEratioValid()) {
			csv += "\"ME Ratio = " + df.format(MEratio) + ":1\"";
		}
		return csv;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + total;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Data))
			return false;
		Data other = (Data) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (total != other.total)
			return false;
		return true;
	}

}
