package oit.is.z2421.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2421.kaizi.janken.model.Entry;

/*
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @GetMapping("/janken")
  public String janken(@RequestParam(required = false) String hand,
      Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("username", loginUser);
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

    model.addAttribute("result", result);
    model.addAttribute("userHand", hand);
    model.addAttribute("cpuHand", cpuHand);

    return "janken";
  }

  @GetMapping("/janken.html")
  public String jankenHtml(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    model.addAttribute("entry", this.entry);
    model.addAttribute("username", loginUser);
    return "janken";
  }
}
