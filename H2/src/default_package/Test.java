package default_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Test {
  private Connection memCon = null;
  private Connection fileCon = null;
  public static void main(String[] args){
    new Test().execute();
  }
  
  private void execute() {
    try {
      memCon = DriverManager.getConnection("jdbc:h2:mem:");
      fileCon = DriverManager.getConnection("jdbc:h2:~/Downloads/H2");
      setup(memCon);
      //setup(fileCon);
      Map<String, List<List<String>>> data = createData();
      insert(memCon,data);
      //insert(fileCon);  
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  private void insert(Connection con, Map<String, List<List<String>>> data) throws SQLException {
    PreparedStatement ps = con.prepareStatement("insert into categories values(?,?)");
    for(List<String> row: data.get("categories")){
      ps.setLong(1, Long.parseLong(row.get(0)));
      ps.setString(2, row.get(1));
      ps.addBatch();
    }
    ps.execute();
  }

  private Map<String, List<List<String>>> createData() {
    Map<String, List<List<String>>> data = new HashMap<>();
    List<List<String>> rows = new LinkedList<>();
    List<String> row = new LinkedList<>();
    row.add("1");
    row.add("Italian");
    rows.add(row);
    row = new LinkedList<>();
    row.add("2");
    row.add("Chinese");
    rows.add(row);
    row = new LinkedList<>();
    row.add("3");
    row.add("Thai");
    rows.add(row);
    data.put("categories", rows);

    rows = new LinkedList<>();
    row = new LinkedList<>();
    row.add("1");
    row.add("Lasagna");
    row.add("1");
    rows.add(row);
    row = new LinkedList<>();
    row.add("2");
    row.add("Carbonara");
    row.add("1");
    rows.add(row);
    row = new LinkedList<>();
    row.add("3");
    row.add("General Tso's");
    row.add("2");
    rows.add(row);
    row = new LinkedList<>();
    row.add("4");
    row.add("Orange Chicken");
    row.add("2");
    rows.add(row);
    row = new LinkedList<>();
    row.add("5");
    row.add("Red Curry");
    row.add("3");
    rows.add(row);
    row = new LinkedList<>();
    row.add("6");
    row.add("Angry Catfish");
    row.add("3");
    rows.add(row);
    data.put("recipes",rows);

    rows = new LinkedList<>();
    row = new LinkedList<>();
    row.add("1");
    row.add("Salt");
    row.add("");
    rows.add(row);
    
    data.put("ingredients",rows);
    return data;
  }

  private void setup(Connection con) throws SQLException {
    con.createStatement().execute("create table categories(category_id bigint primary key, category_name varchar(50))");
    con.createStatement().execute("create table recipes(recipe_id bigint primary key, recipe_name varchar(50), category_id bigint)");
    con.createStatement().execute("create table ingredients(ingredient_id bigint primary key, ingredient_name varchar(50),"
        +"ingredient_amount decimal, ingredient_measurement varchar(10), recipe_id bigint)");
  }
}
