package oit.is.z2421.kaizi.janken.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2421.kaizi.janken.model.Match;
import oit.is.z2421.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  boolean dbUpdate = false;
  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  private MatchMapper MatchMapper;

  public Match syncShowMatches() {
    return MatchMapper.selectByIsActive();
  }

  @Async
  public void asyncShowMatches(SseEmitter emitter) throws IOException {
    dbUpdate = true;
    try {
      while (true) {
        if (!dbUpdate) {
          TimeUnit.MILLISECONDS.sleep(5000);
          continue;
        }
        TimeUnit.MILLISECONDS.sleep(5000);
        Match activeMatch = this.syncShowMatches();
        if (activeMatch != null) {
          emitter.send(activeMatch);
          dbUpdate = false;
          TimeUnit.MILLISECONDS.sleep(5000);
          MatchMapper.updateById(activeMatch.getId());
        }
      }
    } catch (InterruptedException e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } catch (Exception e) {
      logger.error("Unexpected error occurred: " + e.getMessage(), e);
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowFruitsList complete");
  }

}
