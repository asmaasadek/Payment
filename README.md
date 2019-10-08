# Payment

1- Solution implemented in Java EE using Jesry and Maven.

2- After Exporting a WAR and Deploying it on Tomcat, It sould be running on default port: 8080

3- Receive Payment Notification API should be targeted on the following link:
http://localhost:8080/payment/api/v1/sms?message_id=e39ce00e-f8b5-4b0b-96ce-d68f94525704&operator=Etisalat&receiver=13011&sender=%2B37255555555&text=TXT+COINS&timestamp=2019-09-03+12%3A32%3A13

4- Regarding test cases, there are three test cases covering the three main responsibilities.

5- A briefe about the solution:
Payment notifications are enqueued into LinkedBlockingQueue, with two thread pools, each one of them has a configured number of threads.
A thread pool is working on forwarding payment notifications to merchants once receieved and the other for sending the reply message.
