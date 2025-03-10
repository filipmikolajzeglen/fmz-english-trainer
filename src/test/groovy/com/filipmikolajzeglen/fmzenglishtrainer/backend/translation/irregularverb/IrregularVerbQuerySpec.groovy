package com.filipmikolajzeglen.fmzenglishtrainer.backend.translation.irregularverb

import com.filipmikolajzeglen.cqrs.Dispatcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@Sql
@SpringBootTest
@Testcontainers
@Transactional
class IrregularVerbQuerySpec extends Specification {

   @Autowired
   private Dispatcher dispatcher

   @Autowired
   private IrregularVerbQuery irregularVerbQuery

   def 'should execute query using dispatcher'() {
      given:
      irregularVerbQuery.setId(-1L)

      when:
      def result = dispatcher.perform(irregularVerbQuery)

      then:
      result != null
   }
}