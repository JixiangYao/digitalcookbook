package DigitalCookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import CookBookEntity.Recipe;


public class DatabaseController {
	
	static Connection con;
	static Statement sql;
	static ResultSet res;
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");// ʹ��forName��������jdbc��������
			System.out.println("���ݿ��������سɹ�");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// ʹ��Drivemanager��getConnection�ķ����õ����ݿ����ӣ�������������ָ��·�����û���������
			con = DriverManager.getConnection("jdbc:mysql:" + "//127.0.0.1:3306/", "root", "1989");
			System.out.println("���ݿ����ӳɹ�"); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	//login����
	public boolean login(String s1,String s2) throws SQLException{
		con = this.getConnection();
		String s = "select * from cookbook.user where UserName = '"+s1+"' and UserPassword = "+s2;
		sql = con.createStatement();
		res = sql.executeQuery(s);// SQL���Դ�Сд������
		return res.first(); 
	}
	
	//create account ����
	public boolean create(String s1,String s2) throws SQLException{
		con = this.getConnection();
		String s = "insert into cookbook.user values(5, '"+s1+"',"+s2+")";
		sql = con.createStatement();
		res = sql.executeQuery(s);
		return true;		
	}
	
	//show recipe detail ����
	public Recipe showrecipedetail(String s){				
		con = this.getConnection();
		Recipe recipe = new Recipe();
		try {
			sql = con.createStatement();
			String ss = "select * from cookbook.recipe where name = '"+s+"'";
			res = sql.executeQuery(ss);// SQL���Դ�Сд������			
			if (res.next()) {			
				recipe.setRecipeID(res.getString("ID"));
				recipe.setName(res.getString("Name"));
				recipe.setServeNumber(res.getInt("serveNumber"));
				recipe.setPrepareTime(res.getDouble("PrepareTime"));
				recipe.setCookTime(res.getDouble("cookTime"));
				recipe.setCategory(res.getString("Category"));
				recipe.setDescription(res.getString("Description"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recipe;
	}
	
	
	//show all recipe list ����	
	public LinkedList<Recipe> showallrecipelist(String name) {				
		con = this.getConnection();
		LinkedList<Recipe> ls = new LinkedList<Recipe>();
		try {
			sql = con.createStatement();
			res = sql.executeQuery("select * from cookbook.recipe");//sql�Դ�Сд������
			while (res.next()) {
				Recipe recipe = new Recipe();
				recipe.setRecipeID(res.getString("ID"));
				recipe.setName(res.getString("Name"));
				recipe.setServeNumber(res.getInt("serveNumber"));
				recipe.setPrepareTime(res.getDouble("PrepareTime"));
				recipe.setCookTime(res.getDouble("cookTime"));
				recipe.setCategory(res.getString("Category"));
				recipe.setDescription(res.getString("Description"));
				ls.add(recipe);							
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
	
	//show searching recipe list ����
	public LinkedList<Recipe> showsearchingrecipelist(String s) {
		con = this.getConnection();
		LinkedList<Recipe> ls = new LinkedList<Recipe>();
		String ss = "select * from recipe where name is like '%"+s+"%'";
		try {
			sql = con.createStatement();
			res = sql.executeQuery(ss);//sql�Դ�Сд������
			while (res.next()) {
				Recipe recipe = new Recipe();
				recipe.setRecipeID(res.getString("ID"));
				recipe.setName(res.getString("Name"));
				recipe.setServeNumber(res.getInt("serveNumber"));
				recipe.setPrepareTime(res.getDouble("PrepareTime"));
				recipe.setCookTime(res.getDouble("cookTime"));
				recipe.setCategory(res.getString("Category"));
				recipe.setDescription(res.getString("Description"));
				ls.add(recipe);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
	
	//create recipe ����
	public Recipe createrecipe(String name,String preparetime,String cooktime,String category){
		con = this.getConnection();
		Recipe recipe = new Recipe();
		try{
			sql = con.createStatement();
			res = sql.executeQuery();//sql�Դ�Сд������
		}
	}

}