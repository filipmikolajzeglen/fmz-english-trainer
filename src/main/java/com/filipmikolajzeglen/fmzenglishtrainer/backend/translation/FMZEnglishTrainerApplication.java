package com.filipmikolajzeglen.fmzenglishtrainer.backend.translation;

import com.filipmikolajzeglen.cqrs.CQRSConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CQRSConfig.class)
public class FMZEnglishTrainerApplication
{
   public static void main(String[] args)
   {
      SpringApplication.run(FMZEnglishTrainerApplication.class, args);
   }
}