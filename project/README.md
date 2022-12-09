# CMPE 172 Project Journal

## Project Demo on GCP
https://drive.google.com/file/d/1jvhgk0ITSt9nR5cPLOJCrqjr6pu_vhim/view?usp=sharing

## Project Architecture Diagram
![image](https://user-images.githubusercontent.com/74092425/206363699-4e9fd788-8e52-469b-9a09-25532cb3decf.png)

## Journal 1 Entry
My first step to testing the project, was checking the starter code that I had initially pulled, and trying to get each piece the tech stack working with one another, one at a time. The first thing that I tested was whether the starbucks api starter code that I had, could run and ping properly. I then tried post requests from postman, to the API that I had running in terminal locally. Then, I tried running the client starter code we received, and using the simple UI console it has to ping, Add card, order, etc. and checked the api endpoints on localhost to make sure that it could do that. The next step was running a mysql container in docker, and then bashing into it. I would then test this starter code with a mysql container by connecting with the api's application.properties to the container using the same docker network, and then check if the cards/orders I would add from the starter client would add to the database. I bashed into the mysql container and checked the database, and when the api would connect to the database, hibernate would initialize the tables. The tables were also updated by using the client, so this part was smooth. I then tried the same process, but running each part of the stack in docker containers, including the api and client.

Some challenges I faced during this part of the project was understanding the correct makefile commands on different makefiles that I had opened, especially with docker networks and how they communicated locally over a docker network. I had trouble getting the api to connect to the mysql container for this reason.

## Journal 2 Entry
Once I was sure that the client, api, and mysql worked properly together, I tried the mobile app in conjunction, pointing it to the right port (8080) for the api. I was able to connect and create a card with 20 dollars, and it showed up on the mysql database, which was a big step. I could make calls to the api using the client and pay for the order using the mobile app. I then worked on porting over the mapping for the html that was already provided, and modified the html for the cashier's app in order to have it display the html page for the cashier app similar to the node js app. Testing the cashier app with the api and mysql DB running in containers was not too hard, because I could just keep those two containers running and keep running new versions of my cashier's app in terminal, in order to test the functionality.

## Journal 3 Entry
Once I got kong working with the api locally, I decided to make the leap and try deploying things one by one on GCP. My first step that I took was making a CloudSQL instance in GCP, using the same specifications explained in the readme that is present in the professor's lab 6. I then copied the private ip address that was displayed, and made sure to include it in my api's deployment.yaml, under the MYSQL_HOST environment variable, so when I did deploy the api, that it could connect to the CloudSQL instance. Additionally, I bashed into the google cloud mysql shell, and created the starbucks database in a similar way that I did locally, and also created the admin user with password cmpe172 I made sure to make a clean build of my api and pushed it to my docker hub under a new version tag, and then made sure that image tag was present in my deployment.yaml. This was a big step in order to be able to deploy my api on GCP as a workload. Once I added the deployment and service yaml files to my google cloud shell environment, I then used the terminal to start the workload and service. I instantly knew that the api had made some type of connection on google cloud to the cloudsql instance because it initialized the tables - but I had to use jumpbox in order to ping the api using curl. Once I got the ping back - I tried curling a post, and that worked too, and I felt like I had tested sufficiently for the time being.

## Journal 4 Entry
Finally, I reached what I considered to be a very mentally straining part of the project - Getting Kong to work with my api on GCP, and testing every api call with postman and making sure it worked correctly. I followed through Lab 8 religiously, but realized I was doing one thing seriously wrong: I did not copy over the kong proxy endpoint when setting KONG, and instead used the endpoint that was already in the lab on accident. I had been running on very little sleep and rest, and when it eventually clicked what I was doing wrong, I started kicking myself, but I eventually got it to work by adding all the kong-specific yaml files and running them according to Lab 8. Finally, I had my api working behind kong, and I was ready to test with PostMan.

I copied over the kong host and port, and apikey secret that I had made, and watched the magic happen. I tested all my postman commands, and I was able to run each of them and see the results in the database. I breathed a sigh of relief, and realized I had finally reached the final stretch - getting my cashier's app running on GCP. I also made sure to run the mobile app, but also inputting the api endpoint and the apikey into the terminal command I would run, in order to make sure that it could connect to GCP, and it also loaded $20, which meant it was connecting succesfully. I also verified that the card it activated would show up in the database.

### The Final Stretch
I made sure to change my cashier's app controller endpoints that it tried to access, to the kong api service endpoints, and to include the apikey in each request (or at least I thought I did), and then pushed the image to a new version tag on docker hub. I ran into two key blockers in this final stretch that caused me an immense amount of stress as the due date winded down - the page either wouldnt work after pressing a button sometimes, or the post would not work. I realized I had two issues, I had forgotten to include the apikey for the post order in the controller - and I had not enabled client affinity in the external load balancer I manually configured for the cashier's app. It took so much time staring at the screen and remembering what the professor had taught us in order to try and get it all running, But once I fixed these two things, repushed and redeployed: I FINALLY HAD A WORKING STARBUCKS PROJECT ON GCP! I started the mobile app in my terminal, and voila! Everything worked properly and as intended, and I had a working project to demo and record.

## Question: How does this solution scale?
In theory, the load balancers would help the project scale, and it should be able to scale. If we increase the number of pods in the deployment.yaml, we could handle over a million devices, because the load balancer would just distribute the high number of requests to the different. However, we are also limited by something: the fact that we can have only one active order at a time at a store at a time. If we somehow were able to lift this limitation on the number of active orders at a time, then we could greatly increase the scaling capability of the solution.

## Screenshots for requirements in case demo video does not show all of them:

### Cluster Deployment
![image](https://user-images.githubusercontent.com/74092425/206342380-54b03021-bfdf-4b58-9467-7d8ffa98a998.png)

### Healthy Workload Deployments for API, kong-ingress, cashier's app, and jumpbox
![image](https://user-images.githubusercontent.com/74092425/206342625-95a94119-5973-4758-bf15-e5dab06bf67f.png)
![image](https://user-images.githubusercontent.com/74092425/206343617-0956792e-4081-4fa6-b1c6-d10225927688.png)
![image](https://user-images.githubusercontent.com/74092425/206343691-76ec249d-dd75-478e-8972-826656fc6787.png)

### Healthy Services for API, cashier's app, and kong-proxy
![image](https://user-images.githubusercontent.com/74092425/206342716-e21e7cf8-1f99-4c17-8979-50d6d3ed59a5.png)

### Healthy and working ingresses/LBs with frontend endpoints displayed
![image](https://user-images.githubusercontent.com/74092425/206343016-85ceed71-5a40-4524-ad77-597aed83abb2.png)

### Running CloudSQL instance with private ip address that is used in starbucks-api deployment.yaml
![image](https://user-images.githubusercontent.com/74092425/206343150-ca0e10a2-d439-4efc-89c1-cab1fe6dd918.png)

### Java version
![image](https://user-images.githubusercontent.com/74092425/206344410-76ecfac2-1ffa-4c5f-a514-c50609cc32ef.png)

### API Deployment uses sql cloud instance IP to persist order data (also demoed)
![image](https://user-images.githubusercontent.com/74092425/206345288-61d3f29b-5f7e-45d9-b1e2-190c82409b3f.png)

### APIkey secret is in gcp
![image](https://user-images.githubusercontent.com/74092425/206345345-3145a8a1-5335-4b35-81a0-df593541c9ae.png)

### Giving Client Affinity to the external LB for the cashier's app (what I couldn't figure out for a while, mentioned in the final stretch)
![image](https://user-images.githubusercontent.com/74092425/206345855-fce8cc58-e7db-46e2-974d-6c9265f9b218.png)



