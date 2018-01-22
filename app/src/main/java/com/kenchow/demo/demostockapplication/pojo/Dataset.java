package com.kenchow.demo.demostockapplication.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dataset {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("dataset_code")
@Expose
public String datasetCode;
@SerializedName("database_code")
@Expose
public String databaseCode;
@SerializedName("name")
@Expose
public String name;
@SerializedName("description")
@Expose
public String description;
@SerializedName("refreshed_at")
@Expose
public String refreshedAt;
@SerializedName("newest_available_date")
@Expose
public String newestAvailableDate;
@SerializedName("oldest_available_date")
@Expose
public String oldestAvailableDate;
@SerializedName("column_names")
@Expose
public List<String> columnNames = null;
@SerializedName("frequency")
@Expose
public String frequency;
@SerializedName("type")
@Expose
public String type;
@SerializedName("premium")
@Expose
public Boolean premium;
@SerializedName("limit")
@Expose
public Object limit;
@SerializedName("transform")
@Expose
public Object transform;
@SerializedName("column_index")
@Expose
public Object columnIndex;
@SerializedName("start_date")
@Expose
public String startDate;
@SerializedName("end_date")
@Expose
public String endDate;
@SerializedName("data")
@Expose
public List<List<String>> data = null;
@SerializedName("collapse")
@Expose
public Object collapse;
@SerializedName("order")
@Expose
public Object order;
@SerializedName("database_id")
@Expose
public Integer databaseId;

}