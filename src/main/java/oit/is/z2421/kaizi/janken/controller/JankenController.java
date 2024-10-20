package oit.is.z2421.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import oit.is.z2421.kaizi.janken.model.Entry;
import oit.is.z2421.kaizi.janken.model.Match;
import oit.is.z2421.kaizi.janken.model.MatchMapper;
import oit.is.z2421.kaizi.janken.model.User;
import oit.is.z2421.kaizi.janken.model.UserMapper;

@Controller
public class JankenController {

  @Autowired
  private UserMapper UserMapper;
  @Autowired
  private MatchMapper MatchMapper;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    // ログインユーザー名の取得
    String loginUser = prin.getName();
    model.addAttribute("name", loginUser);

    // ユーザーリストと試合結果の取得・表示
    ArrayList<User> users = UserMapper.selectAllName();
    model.addAttribute("users", users);
    ArrayList<Match> matches = MatchMapper.selectAllData();
    model.addAttribute("matches", matches);

    // janken.html テンプレートを表示
    return "janken";
  }

  @GetMapping("/match")
  public String match(@RequestParam int cpuid, ModelMap model, Principal prin) {

    String loginUser = prin.getName();
    model.addAttribute("name", loginUser);
    User cpu = UserMapper.selectById(cpuid);
    model.addAttribute("cpu", cpu);

    return "match";
  }

  @GetMapping("/fight")
  public String fight(@RequestParam String hand, @RequestParam int cpuid, Principal prin, ModelMap model) {

    String loginUser = prin.getName();
    model.addAttribute("name", loginUser);
    String cpuHand = "rock";
    String result = "";

    if (hand != null) {
      if (hand.equals("rock")) {
        result = cpuHand.equals("rock") ? "引き分け" : (cpuHand.equals("scissors") ? "勝ち" : "負け");
      } else if (hand.equals("scissors")) {
        result = cpuHand.equals("rock") ? "負け" : (cpuHand.equals("scissors") ? "引き分け" : "勝ち");
      } else if (hand.equals("paper")) {
        result = cpuHand.equals("rock") ? "勝ち" : (cpuHand.equals("scissors") ? "負け" : "引き分け");
      }
    }
    // じゃんけん結果のモデルへの追加
    model.addAttribute("result", result);
    model.addAttribute("userHand", hand);
    model.addAttribute("cpuHand", cpuHand);

    User cpu = UserMapper.selectById(cpuid);
    model.addAttribute("cpu", cpu);
    User player = UserMapper.selectByName(loginUser);
    Match newMatch = new Match();
    newMatch.setUser1(player.getId());
    newMatch.setUser1Hand(hand);
    newMatch.setUser2(cpu.getId());
    newMatch.setUser2Hand(cpuHand);
    MatchMapper.insertMatch(newMatch);

    return "match";
  }
}
