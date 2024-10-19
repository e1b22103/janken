package oit.is.z2421.kaizi.janken.model;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT id,name from users where id = #{id}")
  User selectById(int id);

  @Insert("INSERT INTO users (name) VALUES (#{name});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertChamber(User users);

  @Select("SELECT * from users where name = #{name}")
  ArrayList<User> selectAllByName(String name);

  @Select("SELECT id,name from users")
  ArrayList<User> selectAllName();

}
