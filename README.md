# Pharmate
Bitirme Projesi

SOFTWARE REQUIREMENT SYSTEM

3.1 Introduction

In this SRS part, we will explain technical details about Pharmate projects considering it score
features, user requirements and system requirements.

3.1.1 Purpose

In this document we describe the software requirements for the proposed mobile application,
further referred to as the Pharmate. As part of this application we will build a system that gather
all stakeholders in one platform to donate unused medicines, where they can register and login.

System Stakeholders

The term "stakeholder" refers to people or groups affected by a software development project.
Stakeholders are both inside and outside the organization. Information from stakeholders tells
the company or person what features and issues they need to resolve and update by telling them
what kind of software and updates are needed.

• Donator: The donor is the person who donates the drugs he / she does not need to those
who need and who cannot take the drugs he / she needs.

• Pharmacy: The pharmacy is actually an intermediary. Pharmacies act as an
intermediary and control mechanism during drug donation. Pharmacies are
intermediaries for the purpose of the drugs and distribution to the people in need. They
also check whether the drugs are suitable for donation.

• Receiver: The receiver is the person who cannot reach the drug he needs and who takes
the drug from a person who donates the drug. The receiver finds the drug from the
application and takes the drug from the intermediary or donor.

• Health Centre: The health centre checks whether the medicines are suitable for
donation. As a mediator between the donor and the recipient, it delivers the drug to the
needy recipient in the most appropriate way.

• Employees: Employees are those who serve or work in the system, in the application.

• Volunteer: Volunteer organizations determine the people or institutions who need the
drug, and ensure that the drugs can reach them in the easiest way. Some examples
thereof in Turkey are: Akut, Kızılay, Yeşilay etc.

• Administrator: Person responsible for managing the application or system.

3.1.2 Project Scope

This project is intended to bring unused drugs to needy institutions, organizations and
individuals in Turkey. The donations planned to be made in this project will be made in
accordance with the directive issued by the world health organization.

3.1.3 Document Conventions

In this section, you will be able to see a table of what are the definitions and abbreviations will
be used in our project.

3.2 User Requirements

User Requirements clarify the necessities of clients with respect to what they need from the
system. It is commonly written in the beginning times of the approval procedure before the
framework is made. They are composed by the framework proprietor and end-clients, with
contribution from Quality Assurance. Necessities laid out in the URS are normally tried in the
Performance Qualification or User Acceptance Testing. Numerous client necessities manage
how a client will collaborate with a system and what that client anticipates. In the event that
there is a screen or human machine interface perspective to the system, a client prerequisite
might be founded on what happens when the client chooses an activity on the screen. Perhaps
with a button press not exclusively does a procedure start, however it additionally changes to
another screen and gives a perceptible warning. At the point when user necessities, for example,
these are recorded, they can regularly break into numerous framework prerequisites later
because of exchanging of screens, the greatest postponements in beginning the procedure,
lastly what the following screen ought to resemble.

3.2.1 Login to the system

The system requires users to log in to use all its features, including donating and accessing
drugs. Thus, the system will be more organized.

3.2.2 Registering / Adding User

In order to use this system, donors and receivers must be added to the system. A user account
is created for this.

3.2.3 Adding Medicine Information

Users who will donate their medicine must upload related information about the medicine to
the system.

3.2.4 Searching Medicine

Users can reach information about the target medicine among the other one’s if it’s available
in the system.

3.2.5 Requesting Medicine

Users can search for the medicine, if exists show the related information about the medicines
will be shown to the users.
Since every medicine needed cannot be in the system, the system will set the status of the
medicine as request until it found.

3.2.6 Generating Report about Medicines

In the medicine donation system, if the medicine is registered in the system, a report that
contains related information about medicines will be created if needed.

3.2.7 Finding best option for receivers

This application is aimed to provide the necessary medicines to the recipients them in the best
way. Many factors, such as expiration dates, ingredients, and side effects of medicines, make
it possible for recipients to get the most out of it. The classifications are based on name of the
medicines, barcodes, and components within the app, and will help the recipient find the
medicine most accurately.

3.2.8 Receiving Medicine

This part for users who needs use this system to receive medicine. For this, there are some
required information that user needs to enter to system.

3.3 System Requirements

In this System Requirements part, the core functions of the system will be explained with much
more detailed related the terms and rules.

3.3.1 Login

The system requires users to log in to use all its features, including donating and accessing
drugs. Thus, the system will be more organized. There are some rules and requirements for
logging into this system. First of all, the user needs to enter the username and password so they
can access the login page that they previously registered. There is a specific format for
username and password. The user must enter his / her password and username in accordance
with this format. If the username or password entered in the wrong format, an error will be
generated which is “Username or password is incorrect.". If needed information’s in the correct 
format, the user is asked for a verification code that goes to the phone/e-mail that registered in
the system. If the verification code is entered incorrectly an error will be generated which is,
the "Code is incorrect”. If the verification code is entered correctly, the user can log in to the
system

• User must enter username and password correctly.

• User must enter the validation code from his/her mobile number.

• If any information is incorrect there will be an error which is (Username or Password is
incorrect).

3.3.2 Registering / Adding Users

In order to use this system, donors and receivers must be added to the system. A user account
is created for this. The user account identifies the user and the user account settings determine
which records the user can access. We need the following information in order to add users to
the system.

Turkish Identification Number (TC No)

• This information is required for Adding User.

• Turkish Identification Number allows system to validate user if he/she is a real person being
or not.

• The prescription and related information about the medicines can be checked with Turkish
Identification Number, if receiver is eligible to take medicines.

Name

• This information is required for Adding User.

• Name information is needed for validate the information from his/her Identity.

Surname

• This information is required for Adding User.

• Surname information is needed for validate the information from his/her Identity.

Mobile Number

• This information is required for Adding User.

• Mobile Number information is needed for Validation Code, which will be used in Login
Screen.

• Mobile Number information can be used for reach to user if necessary.

E-mail

• This information is required for Adding User.

• E-Mail must be ready to use and valid.

• E-mail information is used for Validation Code, if Validation Code for mobile number is
not working.

Address

• This information is required for Adding User.

• Address information can be used for reach to user if necessary.

3.3.3 Adding Medicine Information

Users who will donate their medicine(donors) must upload related information about the
medicine to the system. To add medicine information user need, enter information;

• Name of the medicine.

• Barcode Number of the medicine.

• Amount.

• Expiration Date.

3.3.4 Searching Medicine

Users can reach information about the target medicine among the other one’s if it’s available
in the system.

• Medicines can be searched from logged users.

• If target medicine exists, related information will show in the system.

• If not, nothing has been shown to the user.

3.3.5 Requesting Medicine

Users can search for the medicine, if exists show the related information about the medicines
will be shown to the users.
Since every medicine needed cannot be in the system, the system will set the status of the
medicine as request until it found. To Request Medicine;

• Searched medicine must not be available in the system.

• If requested medicine needs special prescription, related information must be added, and
checked from the authorized users from the system.

3.3.6 Receiving Medicine

This part for users who needs use this system to receive medicine. For this, there are some
required process below that user needs to complete.

• User needs to enter the prescription number in the system after searching the medicine from
the system.

• Afterwards, the prescription will be checked and approved.

• The person in need of the drug and the donor communicate through the validated
organization with each other.

• Finally, the admin / organization leader creates the environment for delivery.

3.3.7 Generating Reports About Medicine

In the medicine donation system, if the medicine is registered in the system, a report that
contains related information about medicines will be created if needed. To generate report;

• Login to the system,

• Search the medicine,

• If exists system will be show a list of the medicines,

• User will choose target medicine,

• Report will be created.

3.3.8 Finding Best Option for Receiver

This application is aimed to provide the necessary medicines to the recipients them in the best
way. Many factors, such as expiration dates, ingredients, and side effects of medicines, make
it possible for recipients to get the most out of it. The classifications are based on name of the
medicines, barcodes, and components within the app, and will help the recipient find the
medicine most accurately.
To find best option for receiver there will be process that user need to complete;

• Login to the system.

• Search the medicine.

• If exists system will be show a list of specific points that user can receive the medicine.

• The system will show the nearest points through the map application.

• If not, system will generate a request for needed medicine.

• Same process above will be eligible when target medicine added in to the system.

3.4 Functional Requirements

Functional requirements define the general infrastructure of the system with the services
provided by the system. They reveal what the system will do structurally and functionally. A
part form development, mostly input, output units are requirements for processes. It determines
in detail what the system can do and the actions it should take.

3.4.1 Login

The system requires users to log in to use all its features, including donating and accessing
drugs. Thus, the system will be more organized. In login screen user will enter the required
information. If the information’s are correct user will login successfully and directed to the
homepage. If not, an error will be generated that says Username/Password/Validation Code is
incorrect. To login the system user will enter the required information;

Username

• Input will be string(text).

• User must enter username correctly.

• Username can contain letters (a-z, A-Z), and numbers (0-9).

• Username cannot contain special characters (!#$@-+=_-‘,><.).

• If the related information about the username is incorrect system will generate an error that
says invalid username.

Password

• Input will be string(text).

• User must enter password correctly.

• Password must contain at least 8 characters.

• Password can contain (a-z, A-Z), and numbers (0-9).

• Password can contain special characters (!#$@-+=_-‘,><.).

• If the related information about the password is incorrect system will generate an error that
says invalid password.

Validation Code

• If Username and Password information is correct.

• The system will direct user to a screen to enter validation code.

• User must enter the validation code from his/her mobile number or e-mail address.

• If validation code is also entered correctly user will be directed homepage.

• If not, system will generate an error that says invalid validation code.


3.4.2 Registering / Adding Users

In order to use this system, donors and receivers must be added to the system. A user account
is created for this. The user account identifies the user and the user account settings determine
which records the user can access. We need the following information in order to add users to
the system. If related information is entered properly the system create your account and say
Account Created / User Added Successfully. If one (or more) of the information is entered in
incorrect form system will generate an error that says “An error occurred, please enter your
information in correct form”.

Username

• Username must between 4-12 characters.

• Username can contain letters (a-z, A-Z), and numbers (0-9).

• Username cannot contain special characters (!#$@-+=_-‘,><.).

Password:

• Password must contain at least 8 characters.

• Password can contain (a-z, A-Z), and numbers (0-9).

• Password can contain special characters (!#$@-+=_-‘,><.).

E-Mail

• E-Mail must be valid and ready to use.

Turkish Identification Number

• Turkish Identification Number cannot start with zero (0).

• Turkish Identification Number must be 11 characters.

• Turkish Identification Number cannot contain any letters (a-z, A-Z) or special characters
(!#$@-+=_-‘,><.).

Name

• Name cannot start with these characters “Ğ-ğ”.

• Name cannot contain special characters (!#$@-+=_-‘,><.).

• Name cannot contain any number (0-9).

Surname

• Surname cannot start with these characters “Ğ-ğ”.

• Surname cannot contain special characters (!#$@-+=_-‘,><.).

• Surname cannot contain any number (0-9).

Address

• Address must contain Country, City, District, Street, Postal Code.

3.4.3 Adding Medicine Information

Users who will donate their medicine(donors) must upload related information about the
medicine to the system. If all the information related the medicine is entered properly system
will generate a text that says “Medicine Added Successfully”. If one of the information’s
entered in incorrect format system will generate an error that says “Medicine Couldn’t be added
to the system. Please correct the information and try again”. Even though to information is
correct, system will check if the medicine is expired or not. If it’s expired system will generate
another error that says “Medicine is expired”.

Name of the medicine

• Name of the medicine must be entered properly.

• Name cannot start with these characters “Ğ-ğ”.

• Name cannot contain special characters (!#$@-+=_-‘,><.).

Barcode Number of the medicine

• Barcode number cannot contain any characters beyond numbers.

• Name cannot start with these characters “Ğ-ğ”.

• Name cannot contain special characters (!#$@-+=_-‘,><.).

Amount

• Amount cannot contain any characters beyond numbers.

• Amount cannot be bigger than 20.

Expiration Date

• Expiration date must be added in Date Time Format.

Special Prescription

• If this medicine needs for a special prescription it must be yes.

• If this medicine needs no special prescription it must say no.

3.4.4 Searching Medicine

Users can reach information about the target medicine among the other one’s if it’s available
in the system. If medicine is available in the system, related information will be shown for the
user. If not system will generate a text that says “No Result”.
To search medicines;

• User must be logged in to the system.

• User has a permission to search to medicines.

To use Search Function user can select one of these methods;

• User can search medicines with name of the medicine.

• User can search medicines with scanning the barcode.

• User can search medicines with entering the barcode number

3.4.5 Requesting Medicines

Users can search for the medicine, if exists show the related information about the medicines
will be shown to the users. Since every medicine needed cannot be in the system, the system
will set the status of the medicine as request until it found. If medicine is requested successfully
system will say “Medicine Requested Successfully”. If not, there will be an error generated
that says “Medicine Couldn’t Requested”.
To request medicine;

• User must search the needed medicine.

• Target medicine must not available in the system.

• The request for the target medicine will be created on the system if the first two conditions
are provided.

• If one of the steps above is not completed properly system will generate an error that says
“Medicine Couldn’t Requested”.

3.4.6 Receiving Medicine

This part for users who needs use this system to receive medicine. For this, there are some
required process below that user needs to complete.
To receive medicine;

• Login to the system.

• Search the medicine.

• If exists system will be showing a list of the medicines for user.

• User will choose target medicine.

• User needs to enter the prescription number in the system after searching the medicine

• If the prescription will be checked and approved.

• If receiving request has been accepted system will say “Your receiving request has been
accepted. Please contact with authorized organization.”.

• The person in need of the medicine and the donor communicate through the validated
organization.

• Finally, the admin / organization leader creates the environment for delivery.

• If receiving request is not accepted system will generate an error that says “Your receiving
request has not been accepted”.

3.5 Non-Functional Requirements

It is the requirements that determine the abstract features of the system. These abstract features
are generally the interface, physical environment, user consideration, reliability and assurance
of quality. These requirements play a critical role, especially in terms of software quality.
Unless these requirements are met, usability will be insufficient.
Security, Efficiency, Availability, Consistency, Simplicity, Modifiability.

3.5.1 Security

System is including many important personal information that users shared with. We have an
obligation to protect this data.
To protect these data;

• User and personal information is going to be stored in the encrypted databases.

• Personal and other important information can be shown from users who has with
permission.

3.5.2 Efficiency

Efficiency is the capacity to abstain from materials, vitality, endeavors, cash, and time in
accomplishing something. The intended efficiency in the project is observed in the same
direction. This project saves time, money and effort for donors and those in need. The
availability of the application keeps efficiency high.

3.5.3 Availability

Availability is that something is accessible. At the same time, the availability of the system is
measured as a factor of reliability, and the increase in reliability increases the availability. The
system intended for the project will protect the data of individuals and make it available to
users for this, system is going to be run on multiple servers for any server error shutdown.

3.5.4 Constraints

There will be limitations for downloading the application, registering, searching medicines and
listing the donators addresses.
Downloading the application

• This application will be developed for android platform which means, to use it Google Play
Store account needed.

Registering

• Users must be at least 18 years old. If not his / her legal parents have to be login instead of
him / her.

• Registration cannot be made via unverified e-mail or phone number.

Searching Medicines

• The medicines have to be legally on the sale.

• A special prescription is required for some infectious diseases. Red-colored prescriptions
are required for drugs and preparations, and green-colored prescriptions for psychotropic
substances and preparations.

Listing the donators addresses

• List of the donators can be shown from only validated users. Other users can see the list of
pharmacies that has the medicine.

Communication

• Donor and receiver can not communicate directly

• Communication is provided from validated organization / admin between donor and
receiver.

3.5.5 Simplicity

This application appeals to a wide age segment, for this reason application will be design with
simple UI for ease of use to increase usability.

3.5.6 Modifiability

Mobile applications or any application that we use aren’t perfect. They are updated periodically
for improving and debugging or any reason. This application will be updated also. To creating
easily updated app we need to create this application modifiable.

3.5.7 Working Environment

Working environment is platforms that we will use until the developing process ends. There is
a list for definition of these platforms.
• Windows Operating System
Microsoft Windows, ordinarily alluded to as Windows, is a gathering of a few restrictive
graphical working framework families, which are all evolved and advertised by
Microsoft. It offers a wide assortment of uses [13].

• Android Operating System

This project will be structured in android working framework. Android working
framework is a Linux based, open source versatile programming produced for cell
phones. Structured essentially for touchscreens, Android is mainstream among cutting
edge gadgets searching for a minimal effort and adaptable working framework. The fact
that the Android operating system is so usable is that it is open source. Android uses
several methods for power usage and memory management. It prevents applications from
interfering in all areas of the system in order to be strong in terms of Android security.
Android applications mostly develop by using Java programming language [14].

• Android Studio

Android Studio is the official Integrated Development Environment (IDE) used in
Android application development. Android studio provides the fastest tools for
developing high quality, successful results running on any Android device. Android
studio includes needed code editor, debugger performance analysis tools. One of its most
important features is supported on all android platforms. Editing, compilation and
execution processes take place quickly. It also provides customizable APK
configurations. Firebase SDK provides built-in support for Firebase Test Lab, Firebase
Application indexing, and Google Cloud Platform [15].

• Java Programming Language

Java; is a simple, object-oriented, network-savvy, interpreted, robust, secure, portable,
high-performance and dynamic computer language. The Android application layer
contains applications developed directly in Java (programming language).Java
programming language is an indispensable whole for android. Therefore, it is the most
suitable programming language to be used for our java project. Having an interpreted
language makes it easy to debug incorrect Java programs. Java can be integrated into
almost any major operating system. In addition, the java programming language and
platform are designed specifically to ensure security. It provides exceptional
functionality to produce custom interfaces in the project [16].

• Database

The database is a sorted out information assortment that is electronically put away and
gotten to from a PC framework. Database the executives framework (DBMS) is
programming that interfaces with end clients, applications, what's more, the database
itself to catch and examine information. DBMS programming likewise covers the
essential conceivable outcomes to deal with the database. The aggregate of database,
DBMS and related applications can be classified "database system"[17].

• Firebase
Firebase is a versatile and web application advancement stage from the Google. It permits
you to Firebase Authentication is an assistance that can just confirm clients utilizing
customer side code. It bolsters other social specialist co-ops, for example, Facebook,
GitHub, Twitter and Google Play Google Games, Apple, Yahoo and Microsoft [18].

• SQLite

SQLite is a software library that gives a social database the board framework. SQLite
doesn't require a server to run. The SQLite database is incorporated with the application
that gets to the database. Applications cooperate with the SQLite database, perusing and
composing legitimately from database records put away on plate. SQLite utilizes
dynamic sorts for tables. It implies you can store any an incentive in any section paying
little heed to the information type. SQLite permits a solitary database association with
get to different database records at the same time [19].

• Visual Studio Code

Although Visual Studio Code is fast, it is a tool that can run on Microsoft, Linux and
MacOS. Any operating system must be available to develop with any programming
language. Visual studio code is an important tool that can work in operating systems that
are needed. It supports many programming languages that we will use thanks to the
plugins it contains. With debugging features, it serves to organize and optimize codes on
advanced web and cloud applications. Another important feature is that it is free [20].

• Adobe Photoshop

It is the most comprehensive graphic and photo editing software developed. Photoshop
CC supports all photo formats, including BMP, GIF, JPG, PCX and TIF. It is also an
extremely successful software for importing and editing physical photos. It is one of the
software that has adopted the layered working system best. Thanks to the layers, a
versatile working principle is provided. Moreover, it offers a lot of alternatives that can
be easily customized [21].









