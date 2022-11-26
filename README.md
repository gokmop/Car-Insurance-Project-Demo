# Safety-Car-Project

The project is deployed using herokuapp and travis/ci integrated in our CI pipeline after the test stage.

[Deployment url](https://safety-car-v1.herokuapp.com/index)

This is the main repository for the "Safety Car" App of Team 1 (Kamen Hristov and Georgi Shutov)

1. Users are regular users.
    - Anonymous users can calculate offers.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/Home_Page_2.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - Users can choose to save an offer they like.
    - They must authenticate themselves to do this.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/Login_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - If they haven't got an account they can register one.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/Register_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - After they fill the required fields they will recieve an email with an activation link. If the link has expired users are greeted with an error prompting them to register again. They can do so with the same email.
    - After a successful authentication users are directed to the index page and any offer they might have liked as anonymous is appended to their offer list.
    - From here users can create a policy from an offer in their offer list. The list can be sorted.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/User_Offers_Table_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - This step requires users to fill their details.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/Edit_User_Details_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
        </p>
    - After filling their details users can proceed to create a policy.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/Policy_Create_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
        </p>
    - Here users must provide a valid image of their car registration certificate. The image must of size not greater than 4mb.
    - After creating their policy users are redirected to their profile.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/User_Profile_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - From here they can go to edit their details, review their policies and offers or calculate a new offer.
    - Dedicated offer calculator for registered users.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/Dedicated_Offer_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - Users can click the button to review their policies. Here they can filter, sort and manage them as they like.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/User_Policies_List_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - The history button shows the current policy's history.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/image_2020-11-04_160045.png "Safety-Car-Project img")
        </details>  
        </p>    
    - The details link shows a detailed view of the current policy.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/Print_Policy_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - The print button shows a how current policy will look if a user wants to print it.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/image_2020-11-04_160856.png "Safety-Car-Project img")
        </details>  
        </p>                              
1. Agents are special users.
    - After logging in agents can check a list of policies. Filter and sort is available here.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/List_All_Policies_Of_User_Admin_View_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>      
    - After logging in agents can check a list of all registered users with active details.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/List_All_Users_Admin_View_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - The link Show Policies shows the current user's policies. Filter and sort is available here.
    - The Manage button shows the policy manage page for agents.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/Manage_Policy_Admin_View_Page.jpg "Safety-Car-Project img")
        </details>  
        </p>
    - Here agents can view the image and validate(we need an image of a car registration certificate not your dog). If the image is valid they can approve the policy. If it's not they can reject it. They must provide a short message for both operations. The message is recorded in the policy's history.
    - All errors are handled globally with an apropriate message on our error page which still needs some sprucing up.
    - Swagger REST functionality. Rest errors are also handled globally with a custom repsonse entity.
        <p>
        <details>
        <summary>Image Click</summary>    
        ![alt text](https://gitlab.com/g.h.shutov/safety-car-project/-/raw/master/Logic%20and%20Information%20About%20the%20Project/Views%20From%20Front%20End/Swagger_UI_REST_Services.jpg "Safety-Car-Project img")
        </details>  
        </p>           
