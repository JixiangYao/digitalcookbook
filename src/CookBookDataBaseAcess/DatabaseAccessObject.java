package CookBookDataBaseAcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;



import CookBookEntity.Ingredient;
import CookBookEntity.PreparationSteps;
import CookBookEntity.Recipe;


public class DatabaseAccessObject {

	static Connection con;
	static Statement sql;
	static ResultSet res, res2;

	public Connection getConnection() {
	
		try {
			Class.forName("com.mysql.jdbc.Driver");// ʹ��forName��������jdbc��������
			System.out.println("���ݿ��������سɹ�");
			// ʹ��Drivemanager��getConnection�ķ����õ����ݿ����ӣ�������������ָ��·�����û���������
			con = DriverManager.getConnection("jdbc:mysql:" + "//127.0.0.1:3306/?characterEncoding=utf8&useSSL=true&serverTimezone=GMT", "root", "heyining");
			System.out.println("database access sucessful!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	// login����,����0Ϊ���벻һ������Ϊ0��Ϊuserid��-1��Ϊδ�ҵ��û���
	public int login(String username, String userpassword) throws SQLException {
		int i = 1;
		con = this.getConnection();
		String s1 = "select * from cookbook.user where UserName = '"+ username +"'";
		res = sql.executeQuery(s1);
		if(res.first()){
			String s2 = "select * from cookbook.user where UserName = '" + username + "' and UserPassword = " + userpassword;
			sql = con.createStatement();
			res = sql.executeQuery(s2);
			if (res.first()) {
				i = res.getInt("userid");
			} else {
				i = 0;
			}
		}else{
			i = -1;
		}
		//String s = "select * from cookbook.user where UserName = ? and UserPassword = ?";
		//PreparedStatement ps = (PreparedStatement) con.prepareStatement(s);
		//ps.setString(arg0, arg1);
		
		return i;
	}

	// INSERT INTO `cookbook`.`user` (`UserName`, `UserPassword`) VALUES
	// ('Xiyuan', '12345'); ɵ�Ʒ��Ų�����
	// create account ����,�ѳɹ����
	public boolean create(String username, String userpassword1, String userpassword2) throws SQLException {
		con = this.getConnection();
		String s = "insert into `cookbook`.`user` (`UserName`,`UserPassword`) values('" + username + "',"
				+ userpassword1 + ")";
		sql = con.createStatement();
		int res1 = sql.executeUpdate(s);
		System.out.println(res1);
		boolean i;
		if (res1 >= 1) {
			i = true;
		} else {
			i = false;
		}
		return i;
	}

	/**
	 * get a complete recipe
	 * @param userid
	 * @param name
	 * 
	 */
	public Recipe saveRecipe(int userid, String name) {
		con = this.getConnection();
		Recipe recipe = new Recipe();
		LinkedList<Ingredient> ls1 = new LinkedList<Ingredient>();
		LinkedList<PreparationSteps> ls2 = new LinkedList<PreparationSteps>();
		try {
			sql = con.createStatement();

			// ��ȡrecipeһ����Ϣ����
			String ss1 = "select * from cookbook.recipe where name = '" + name + "'";
			res = sql.executeQuery(ss1);
			if (res.next()) {
				recipe.setRecipeID(res.getInt("ID"));
				recipe.setName(res.getString("Name"));
				recipe.setServeNumber(res.getInt("serveNumber"));
				recipe.setPrepareTime(res.getInt("PrepareTime"));
				recipe.setCookTime(res.getInt("cookTime"));
				recipe.setCategory(res.getString("Category"));
				recipe.setDescription(res.getString("Description"));
			}

			// ��ȡ��Ӧingredients����
			String recipeid = res.getString("ID");
			String ss2 = "select * from cookbook.ingredients where RecipeID = " + recipeid;
			res = sql.executeQuery(ss2);
			while (res.next()) {
				Ingredient ingredients = new Ingredient();
				ingredients.setIngredientsID(res.getDouble("ID"));
				ingredients.setName(res.getString("Name"));
				ingredients.setAmount(res.getDouble("Amount"));
				ingredients.setUnit(res.getString("Unit"));
				ls1.add(ingredients);
			}
			recipe.setIngredientlist(ls1);

			// ��ȡ��Ӧpreparationsteps����
			String ss3 = "select * from cookbook.preparationsteps where RecipeID = " + recipeid;
			res = sql.executeQuery(ss3);
			while (res.next()) {
				PreparationSteps preparationsteps = new PreparationSteps();
				preparationsteps.setStepsID(res.getDouble("ID"));
				preparationsteps.setDescription(res.getString("Description"));
				preparationsteps.setOrder(res.getDouble("preparationstepsorder"));
				ls2.add(preparationsteps);
			}
			recipe.setPreparationSteps(ls2);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recipe;
	}

	// show all recipe list method
	/*
	 * @description
	 * 
	 */
	public LinkedList<Recipe> showallrecipelist(int userid, String name) {
		con = this.getConnection();
		LinkedList<Recipe> ls = new LinkedList<Recipe>();
		try {

			// ��ʾ�����й�������recipe
			sql = con.createStatement();
			res = sql.executeQuery("select * from cookbook.recipe where `privacy` = 0");
			while (res.next()) {
				Recipe recipe = new Recipe();
				recipe.setRecipeID(res.getInt("ID"));
				recipe.setName(res.getString("Name"));
				recipe.setServeNumber(res.getInt("serveNumber"));
				recipe.setPrepareTime(res.getInt("PrepareTime"));
				recipe.setCookTime(res.getInt("cookTime"));
				recipe.setCategory(res.getString("Category"));
				recipe.setDescription(res.getString("Description"));
				ls.add(recipe);
			}

			// ��ʾ��˽�в���recipe
			String ss = "select * from `cookbook`.`user-recipe` where userid = " + userid;
			res = sql.executeQuery(ss);
			String s1;
			while (res.next()) {
				s1 = "select * from `cookbook`.`recipe` where recipeid = " + Integer.toString(res.getInt("recipeid"));
				res2 = sql.executeQuery(s1);
				Recipe recipe = new Recipe();
				recipe.setRecipeID(res2.getInt("ID"));
				recipe.setName(res2.getString("Name"));
				recipe.setServeNumber(res2.getInt("serveNumber"));
				recipe.setPrepareTime(res2.getInt("PrepareTime"));
				recipe.setCookTime(res2.getInt("cookTime"));
				recipe.setCategory(res2.getString("Category"));
				recipe.setDescription(res2.getString("Description"));
				ls.add(recipe);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	// show searching recipe list ����.�Ҳ�û��ʵ��A�û�����Ĳ�������B�û��Լ�create�Ĳ���ͬ���������
	public LinkedList<Recipe> showsearchingrecipelist(int userid, String s) {
		con = this.getConnection();
		LinkedList<Recipe> ls = new LinkedList<Recipe>();
		try {
			sql = con.createStatement();
			String ss = "select * from cookbook.recipe where name is like '%" + s + "%'";
			res = sql.executeQuery(ss);
			while (res.next()) {
				Recipe recipe = new Recipe();
				recipe.setRecipeID(res.getInt("ID"));
				recipe.setName(res.getString("Name"));
				recipe.setServeNumber(res.getInt("serveNumber"));
				recipe.setPrepareTime(res.getInt("PrepareTime"));
				recipe.setCookTime(res.getInt("cookTime"));
				recipe.setCategory(res.getString("Category"));
				recipe.setDescription(res.getString("Description"));
				ls.add(recipe);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	// create recipe method
	public boolean createrecipe(int userid, Recipe recipe) {
		con = this.getConnection();
		boolean j = false;
		int i = 0, res1 = 0;
		int recipeid;
		try {

			// ���뵽recipe����
			String ss1 = "INSERT INTO `cookbook`.`recipe` (`Name`, `ServeNumber`, `Privacy`, `PrepareTime`, `Category`, `Description`, `CookTime`) values('"
					+ recipe.getName() + "'," 
					+ recipe.getServeNumber() + "," 
					+ recipe.getPrivacy() + ","
					+ recipe.getPrepareTime() + ",'" 
					+ recipe.getCategory() + "','" 
					+ recipe.getDescription() + "',"
					+ recipe.getCookTime();
			res1 = sql.executeUpdate(ss1);
			if (res1 >= 1) {
				i = i++;
			}

			// ����recipeid
			String ss = "select * from `cookbook`.`recipe` where Name = '" + recipe.getName() + "'";
			res = sql.executeQuery(ss);
			recipeid = res.getInt("ID");

			// ���뵽Ingredients����
			LinkedList<Ingredient> ls1 = recipe.getIngredientlist();
			// Iterator iter1 = ls1.iterator();
			for (int x = 0; x < ls1.size(); x++) {
				ls1.get(x);
				String ss2 = "INSERT INTO `cookbook`.`ingredients` (`Name`, `RecipeID`, `Amount`, `Unit`, `Description`) "
						+ "values('" 
						+ ls1.get(x).getName() + "','" 
						+ recipeid + "','" 
						+ ls1.get(x).getAmount() + "','"
						+ ls1.get(x).getUnit() + "','" 
						+ ls1.get(x).getDescription() + "')";
				res1 = sql.executeUpdate(ss2);
			}
			if (res1 >= 1) {
				i = i++;
			}

			// ���뵽preparationsteps����
			LinkedList<PreparationSteps> ls2 = new LinkedList<PreparationSteps>();
			for (int y = 0; y < ls1.size(); y++) {
				ls2.get(y);
				String ss3 = "INSERT INTO `cookbook`.`preparationsteps` (`Description`,`preparationstepsorder`,`RecipeID`)"
						+ "values('" 
						+ ls2.get(y).getDescription() + "','" 
						+ ls2.get(y).getOrder() + "','" 
						+ recipeid+ "')";
				res1 = sql.executeUpdate(ss3);
			}
			if (res1 >= 1) {
				i = i++;
			}

			// ���뵽recipe-user����
			String ss4 = "INSERT INTO `cookbook`.`user-recipe` (`userid`,`recipeid`) values('" 
			+ userid + "," 
			+ recipeid+ ")";
			res1 = sql.executeUpdate(ss4);
			if (res1 >= 1) {
				i = i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (res1 >= 4) {
			j = true;
		}
		return j;
	}

	// ɾ��recipe����������trueΪ�ɹ�������falseΪʧ��
	public boolean deleterecipe(Recipe recipe) {
		con = this.getConnection();
		boolean i = false;
		int recipeid = recipe.getRecipeID();
		String ss = "delete from `cookbook`.`recipe` "
				+ "where id = '" + recipeid + "' and privacy = 1";
		try {
			sql = con.createStatement();
			int res1 = sql.executeUpdate(ss);
			if (res1 == 1) {
				i = true;
			} else {
				i = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// UPDATE `cookbook`.`recipe` SET `Name` = 'qiezi', `ServeNumber` = '2',
	// `Category` = 'meat' WHERE (`ID` = '4');
	// �޸�recipe������recipe1Ϊ��Ҫ�޸ĵ�recipe��recipe2Ϊ�޸�֮���recipe
	public boolean editrecipe(int userid, Recipe recipe1, Recipe recipe2) {
		con = this.getConnection();
		boolean i = false;
		int recipe1id = recipe1.getRecipeID();
		int recipe2id = recipe2.getRecipeID();
		try {
			String ss1 = "update `cookbook`.`recipe` "
					+ "set `name` = '" + recipe2.getName() + 
					"', `servenumber` = '"+ recipe2.getServeNumber() + 
					"', `preparetime` = '" + recipe2.getPrepareTime() + 
					"', `category` = '" + recipe2.getCategory() + 
					"', `description` = '" + recipe2.getDescription() + 
					"', `cooktime` = '" + recipe2.getCookTime() + 
					"' where (`id` = " + recipe1id + ")";
			res = sql.executeQuery(ss1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	// ��Ϊfavourite recipe����,trueΪ�ɹ�
	public boolean favouriterecipe(int userid, Recipe recipe) {
		con = this.getConnection();
		int recipeid = recipe.getRecipeID();
		int res1 = 0;
		boolean i = false;
		try {
			String ss = "insert into `cookbook`.`favourite` (`recipeid`,`userid`) values('" 
		+ recipeid + "','" 
		+ userid + "')";
			res1 = sql.executeUpdate(ss);
			if (res1 >= 1) {
				i = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// rate��comments����,trueΪ�ɹ�
	public boolean rateandcomments(int userid, Recipe recipe, int rate, String comments) {
		con = this.getConnection();
		int recipeid = recipe.getRecipeID();
		int res1 = 0;
		boolean i = false;
		try {
			String ss = "insert into `cookbook`.`rate (`recipeid`,`userid`,`rate`,`comments`) values('" 
					+ recipeid+ "','" 
					+ userid + "','" 
					+ rate + "','" 
					+ comments + "')";
			res1 = sql.executeUpdate(ss);
			if (res1 >= 1) {
				i = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	private void savebasicRecipe(Recipe recipe , ResultSet res) throws Exception{
		
		recipe.setRecipeID(res.getInt("ID"));
		recipe.setName(res.getString("Name"));
		recipe.setServeNumber(res.getInt("serveNumber"));
		recipe.setPrepareTime(res.getInt("PrepareTime"));
		recipe.setCookTime(res.getInt("cookTime"));
		recipe.setCategory(res.getString("Category"));
		recipe.setDescription(res.getString("Description"));
		
	}
	
	private void saveIngredients(Ingredient ingredients , ResultSet res) throws Exception{
		
	}
	
	private void savePreparationSteps(PreparationSteps preparationSteps, ResultSet res) throws Exception{
		
	}
	
	public String recipedescription(int recipeid)throws Exception {
        con = this.getConnection();
        //int recipeid = recipe.getRecipeID();
        
        String ss = "select description from cookbook.recipe where recipeid = " + recipeid;
        res = sql.executeQuery(ss);
        return res.getString("description");
        
        
    }
    
    public String reciperate(int recipeid,int userid)throws Exception{
        con = this.getConnection();
        String ss = "insert into values()";
        res = sql.executeQuery(ss);
        return res.getString("rate");
    }


}
