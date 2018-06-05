package DigitalCookbook;
import java.sql.Connection; 
import java.sql.DriverManager ; 
import java.sql.ResultSet; 
import java.sql.SQLException ; 
import java.sql.Statement ;


public class Database_test {
	public static void main(String[] args) throws Exception {
		Connection con ; 
		String driver = "com.mysql.cj.jdbc.Driver";  //driver��Ϣ
		String url = "jdbc:mysql://localhost:3306/recipedatabase?useSSL=true&serverTimezone=GMT";//�������ϵ���������mysql�����������ssl�ᾯ�棬
		//�����һ�£�ͬʱʱ������Ҳ������
		String user = "root";//�û���һ�㱾���˻�����root����Ҳ�����Լ��ڱ������ݿ�������˻�
		String password = "heyining";//����
		try {
			Class.forName(driver);//���ȶ�����������
			con = DriverManager.getConnection(url, user, password);//���������������ݿ�����
			if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			Statement statement = con.createStatement();//������ݿ�session
			String sql = "select * from recipe";//�������Լ�����һ����
			/*
			 * ������
			 */
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {     //ע��rs.next()�ܹ���ȡ���ݿ��ѯ������һ������
				System.out.println(rs.getString("recipename"));//����������ѯ
			}
			System.out.println(rs.next());
			rs.close();
			con.close();
			//�ǵùر����ӣ��������й������ӵ���䶼����try��
		}catch(ClassNotFoundException e) {
			System.out.println("Sorry, can't find the Driver");
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("DataBase Connection sucessful");
		}
	}

}
