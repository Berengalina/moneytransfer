[![Build status](https://ci.appveyor.com/api/projects/status/sol2wpx5h8sg4jka/branch/master?svg=true)](https://ci.appveyor.com/project/Berengalina/moneytransfer/branch/master)

Сборка в CI подсвечивается зеленым, но в ходе обработки есть ошибка:
2020-11-25 17:39:37.500 [DefaultDispatcher-worker-5] ERROR Application - 500 Internal Server Error: POST - /api/transfer
java.util.NoSuchElementException: Collection contains no element matching the predicate

Отчет выглядит так

<testsuite name="ru.netology.web.test.MoneyTransferTest" tests="4" skipped="0" failures="0" errors="0" timestamp="2020-11-25T17:34:43" hostname="ANNA-PC" time="16.955">
  <properties/>
  <testcase name="shouldTransferMoneyBetweenOwnCardsV1()" classname="ru.netology.web.test.MoneyTransferTest" time="10.239"/>
  <testcase name="shouldTransferMoneyBetweenOwnCardsV2()" classname="ru.netology.web.test.MoneyTransferTest" time="1.804"/>
  <testcase name="shouldTransferMoneyBetweenOwnCardsV3()" classname="ru.netology.web.test.MoneyTransferTest" time="1.856"/>
  <testcase name="shouldTransferMoneyBetweenOwnCardsV4()" classname="ru.netology.web.test.MoneyTransferTest" time="3.053"/>
  <system-out><![CDATA[]]></system-out>
  <system-err><![CDATA[SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Starting ChromeDriver 86.0.4240.22 (398b0743353ff36fb1b82468f63a3a93b4e2e89e-refs/branch-heads/4240@{#378}) on port 45717
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
нояб. 25, 2020 8:34:50 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
]]></system-err>
</testsuite>