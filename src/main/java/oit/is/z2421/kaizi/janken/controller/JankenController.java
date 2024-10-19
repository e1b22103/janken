package oit.is.z2421.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2421.kaizi.janken.model.Entry;
import oit.is.z2421.kaizi.janken.model.User;
import oit.is.z2421.kaizi.janken.model.UserMapper;
//import oit.is.z2421.kaizi.janken.model.UserInfo;

/*
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

  @Autowired
  // private Entry entry;
  private UserMapper UserMapper;

  @GetMapping("/janken")
  public String janken(@RequestParam(required = false) String hand,
      @RequestParam(required = false) String name,
      Principal prin, ModelMap model) {
    // ログインユーザー名の取得
    String loginUser = prin.getName();
    model.addAttribute("username", loginUser);

    // じゃんけんの結果を計算
    String cpuHand = "rock"; // CPUの手（固定）
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

    // ユーザーリストの取得と表示
    ArrayList<User> users = UserMapper.selectAllName();
    model.addAttribute("users", users);

    // 名前が入力された場合はそれもモデルに追加
    if (name != null && !name.isEmpty()) {
      model.addAttribute("name", name);
    }

    // janken.html テンプレートを表示
    return "janken";
  }

  /*
   * @GetMapping("/janken")
   * public String janken(@RequestParam(required = false) String hand,
   * Principal prin, ModelMap model) {
   * String loginUser = prin.getName();
   * // this.entry.addUser(loginUser);
   * // model.addAttribute("entry", this.entry);
   * model.addAttribute("username", loginUser);
   * String cpuHand = "rock";
   * String result = "";
   * 
   * if (hand != null) {
   * if (hand.equals("rock")) {
   * result = cpuHand.equals("rock") ? "引き分け" : (cpuHand.equals("scissors") ? "勝ち"
   * : "負け");
   * } else if (hand.equals("scissors")) {
   * result = cpuHand.equals("rock") ? "負け" : (cpuHand.equals("scissors") ? "引き分け"
   * : "勝ち");
   * } else if (hand.equals("paper")) {
   * result = cpuHand.equals("rock") ? "勝ち" : (cpuHand.equals("scissors") ? "負け" :
   * "引き分け");
   * }
   * }
   * 
   * model.addAttribute("result", result);
   * model.addAttribute("userHand", hand);
   * model.addAttribute("cpuHand", cpuHand);
   * 
   * return "janken";
   * }
   * 
   * @GetMapping("/janken.html")
   * public String jankenHtml(@RequestParam(required = false) String name,
   * ModelMap model) {
   * ArrayList<User> users = UserMapper.selectAllName();
   * model.addAttribute("users", users);
   * if (name != null && !name.isEmpty()) {
   * model.addAttribute("name", name);
   * // 名前が入力されている場合にのみモデルに追加
   * }
   * return "janken";
   * }
   */

  /*
   * @GetMapping("/janken.html")
   *
   * @Transactional
   * public String jankenHtml(Principal prin, ModelMap model) {
   * ArrayList<User> chambers5 = UserMapper.selectAllByChamberName();
   * model.addAttribute("chambers5", chambers5);
   * String loginUser = prin.getName();
   * model.addAttribute("entry", this.entry);
   * model.addAttribute("username", loginUser);
   * return "janken";
   * }
   */
}
