package oit.is.z2421.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT id,user1,user2,user1Hand,isActive from matchinfo")
  ArrayList<MatchInfo> selectAllData();

  @Select("SELECT id,user1,user2,user1Hand,isActive from matchinfo where isActive=true")
  MatchInfo selectByIsActive();

  @Select("SELECT count (*) from matchinfo where isActive=true and user1=#{id2} and user2=#{id1}")
  int CheckActiveMatch(int id1, int id2);

  @Select("SELECT user1Hand from matchinfo where isActive=true and user1=#{id}")
  String CheckCpuHand(int id);

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive})")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

  @Update("UPDATE matchinfo set isActive=false where user1=#{id2} and user2=#{id1}")
  void updateById(int id1, int id2);

}
