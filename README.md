# Ambulance-Drone-Java-Swing-Application

## Motivation:

During a medical emergency, the crucial factor which determines the condition of a patient, is how quickly the medical care is provided. The average time taken by an ambulance to reach the patient’s site after a call is made to 911 is approximately 10 minutes. This duration could be too long to save a person who has suffered major injuries and who needs immediate care. 

For instance: a person who suffered from cardiac arrest, if immediate help is not provided within 3-4 minutes, the patient most likely dies.

Thus if time is reduced and medical help is provided to the patient sooner, the chances of the patient’s survival increases to a large extend.

## Proposed System:

To solve this problem, we can create an “Ambulance drone” which gets triggered when the patient dials 911. The Ambulance drone is an all-purpose medical toolkit that can be automatically flown to any emergency situation and is used to guide citizens to make non-technical lifesaving procedures. 

The drone has an emergency medical kit which contains basic lifesaving technologies like the Automated External Defibrillator (AED), Cardiopulmonary Resuscitation (CPR), oxygen masks, medications, insulin injections, etc which can be helpful for controlling the emergency situation by the time the ambulance arrives. The drone also has an inbuilt camera and audio system.

The average time taken by the drone to reach the emergency location is approximately one minute, thus increasing the patient’s chances of survival.

## Key roles:
  -Drone
  -911 emergency system
  -Police
  -Patient
  -Patient’s family
  -Hospital
  -Doctor
  -Ambulance service

## End-to-end flow:

1. The patient dials 911 in case of an emergency. The “Emergency Management System” representative attends the calls. The place where the emergency has occurred is fetched by the representative.
2. The representative determines the nearest 911 Emergency Department (Public Safety Answering Point), where he can route the call to for quicker assistance.
3. Once the call is routed to the PSAP, the PSAP admin determines the nearest Drone Station with respect to the emeregncy location and alerts the active drone.
4. Once the drone gets activated, it determines the nearest hospital which has the speciality to treat the patients illness and alerts them.
5. The drone alerts the on call doctor of the hospital.
6. Once the on call doctor is alerted, the doctor gets connected to the drone via a camera and thus can view the live footage of the accident location.
7. Once the doctor is connected to the camera, the doctor can monitor the emergency situation and help the people at the accident location to take actions. For instance: the doctor can assist the people to give CPR to patients who are unable to breathe.
8. When the drone alerts the hospital, it computes the shortest path for the ambulance to reach the emergency destination and sends this information to the hospital. 
9. The hospital admin then identifies the available ambulance and passes this information to it, which helps the ambulance reach the destination at the earliest.
10. If the emergency is of Accident type, once the drone reaches the destination. It can scan the license plate of the accident car and send it to the “Police department”.
11.The police department then runs through their registered car database and finds the person associated with the registered car. The police fetches the emergency contact information of the patient and sends an alert to the contact about the accident.
12. Once the ambulance reaches the destination, the drone becomes available again and returns to its station.

The object model of the system was created using the eco-system architecture. [Click here]() to refer to the object model of the system.

## Benefits:

By using an unmanned aerial vehicle (UAV), also known as a drone, we can improve the medical care given to the patient in several ways such as:
  -The average time taken by the ambulance to reach the emergency location is around 10 minutes. But, the drone takes about a minute’s      time, thus starting the patient’s treatment sooner.
  - The drone can contact the hospital and alert it regarding the emergency, thus giving them time to do the preparation well in hand      before the patient arrives at the hospital.
  - By computing the shortest path for the ambulance, it helps the ambulance reach the destination in minimum time.
  - By identifying the patient’s emergency contact and alerting them about the incidence provides quicker and better patient care.

## Assumptions:

  - A drone which can carry the weight of the medical instruments and fly at a speed which reaches the destination within one minute.
  - The drone has the information about the hospital’s specialty and the bed availability in real time to determine which hospital the      patient must be taken to.
  - Every car has been registered and the police department has access to that information.

