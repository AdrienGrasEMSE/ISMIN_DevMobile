# 1 - Context and Approach

I was somewhere between stress and relaxation while working on this project. The excitement of wanting to secure the job and my drive to produce clean, maintainable code kept me motivated, even though I don't consider myself an expert yet. It was the kind of **positive stress** that pushes you to think critically, tackle challenges, and find creative solutions.

Balancing my **day-to-day coursework**, my **startup project**, **freelance missions**, and this test was a challenge, but my passion for **programming** and my eagerness to grow as an **Android engineer** made it a rewarding experience.

To tackle this project, I first tried to imagine all the possible and imaginable scenarios the app might encounter. Some of the scenarios I considered include:

What happens if the API fails or is offline?
What if the data is not available locally in the database?
How do I handle network errors and ensure recovery?
What should be done if the user list is empty?
How do I guarantee a smooth user experience, even when the API is slow?
How do I handle a large amount of data or multiple users?

Once these scenarios were identified, I translated them into an architecture capable of addressing each case. I broke down the requirements into manageable components and created a clear roadmap. I prioritized the features based on their complexity and importance, ensuring that the core functionality was implemented first.

# 2 - Key Implementation Details

### Tech Stack:
- **Kotlin** for the language
- **Jetpack Compose** for the UI
- **Room Database** for local storage
- **Retrofit** for API calls
- **Hilt** for dependency injection

### Design Choice:
I chose a **minimalistic design** to focus on the core functionality: displaying a list of users with their name, surname, and email. This allowed me to stay aligned with the requirements without overcomplicating the app, ensuring a clean and scalable solution.

# 3 - Project Overview and Architecture

The project is an **Android application** designed to demonstrate the integration of key features expected in modern applications, adhering as much as I could to **clean architecture** principles. The main features implemented are:

- **User List Display**: The app fetches and displays a list of users from a remote API in a dynamic, user-friendly interface built with **Jetpack Compose**.
- **Error Handling**: To handle potential API failures, the app implements a fallback mechanism that leverages a local **Room database**. If the API fails, the app retrieves cached user data from the local database.
- **Retry Mechanism**: If the API fails and the local database is empty, the app displays an error state with a **retry button**, allowing users to trigger a re-fetch of data when the issue is resolved.
- **State Management**: The app uses **reactive state flows** to ensure the UI updates seamlessly as the data changes, providing a smooth user experience.
- **Data Management**: By leveraging the **Repository pattern**, the app separates concerns and provides a clear interface for data operations, abstracting the logic for fetching, caching, and retrying data requests.

### Architecture

The architecture of the app follows the **MVVM (Model-View-ViewModel)** pattern, ensuring a scalable, maintainable, and testable design. The app is divided into three primary components: **Model**, **View**, and **ViewModel**, each with a distinct responsibility. Here's a diagram that breaks down how these components interact:

![Diagram of Data Flow and Component Interactions in the App](app/src/main/architecture_diagram.png)

Thanks to the **MVVM architecture**, the app is easy to maintain and scale in the future. For example, if we decide to change the data source (e.g., switching to a different API or a new database), the changes can be made in the **Repository** without affecting the **ViewModel** or **View**. This separation of concerns ensures the **ViewModel** and **View** remain decoupled from the data layer, enabling quick and isolated modifications.



# 4 - Scalability and Robustness

In my latest commit, I focused extensively on **maintainability** and creating clean, easily understandable code for other developers. This involved taking a step back to evaluate my code, reorganizing files, and simplifying the architecture to enhance coherence. For example, I centralized padding values in a dedicated file to eliminate "magic numbers" and leveraged the `@Inject` annotation to automatically handle mapper provisioning, reducing manual dependencies. Additionally, I ensured the use of **theme-based colors** instead of hard-coded values, which not only strengthens visual consistency but also simplifies long-term maintenance. Furthermore, by centralizing all user data in the **UserResponse** class, it becomes straightforward to add new attributes like gender or location, making features like filtering users based on these attributes easy to implement. Also, I created a separate `UserDAO` class to differentiate database entities from the `User` class used in the application logic. This separation ensures better decoupling between the database layer and the business logic, making the code more modular and easier to test.



That said, I recognize that certain areas, such as **state management efficiency** and **API response parsing**, could still be optimized further. I remain open to suggestions and feedback to continue improving.


# 5 - Challenges and Lessons Learned

I faced three key challenges during this project:

1. **Implementing Hilt**: Setting it up from scratch and dealing with version compatibility was a real puzzle. Despite previous exposure at Withings, it was tricky, but the **documentation** and **Stack Overflow** helped me navigate the challenges.

2. **Integrating Room**: This felt like a breeze after implementing Hilt! Even though I did not know Room beforehand, it was really easy to grasp and implement! I hesitated to use sharedPrefs as I already knew how it worked, but it's not optimal for evolutivity!

3. **Writing Tests in Kotlin**: Applying tests for the first time in Kotlin was an exciting challenge as it made my code more **reliable** and **less prone to bugs**, something I’d previously only understood theoretically. However this part is still to work on as I could do more and different types of tests!

Although I was confident in my approach at first, looking back, I’m a bit disappointed with how I tackled things. In the future, I would start with **writing tests** to cover all possible scenarios before coding the components. This would ensure a **robust codebase** from the start. **TDD** has reshaped my approach to coding, and I’m excited to continue learning from the experts at **Qonto** to enhance my skills further.

# 6 - Video Demo 🎥

In this video, we demonstrate the main features and functionalities of the application. Here's a step-by-step explanation of what happens in the demo:

1. **Launching the App**
    - The application is launched, and it begins by fetching users from the API to populate the user list.

2. **Scrolling and Pagination**
    - As we scroll down, the app dynamically loads more users from the API using pagination to enhance the user experience.

3. **Simulating Offline Mode**
    - We disable the internet connection and exit the app.
    - Upon relaunching the app, the repository retrieves the previously loaded user profiles from the local database instead of making an API call.

4. **Refreshing the Data**
    - We press the refresh button, which deletes all the users stored in the database and restarts the data-fetching process.
    - However, since the device is offline, the API call fails, leaving the database empty. The app transitions to an **error state**, displaying an error screen with a retry button.

5. **Retrying the API Call**
    - We press the retry button, which checks for an internet connection.
    - A toast message is displayed, suggesting we verify the network connection.

6. **Restoring Connectivity and Retrying**
    - The Wi-Fi is re-enabled, and we retry the process. This time, the API call succeeds, the user list is reloaded, and we return to the initial state of the application.

---

Feel free to watch the video and follow along with the explanations above to get a complete understanding of how the app handles various scenarios, including **online mode**, **offline fallback**, and **error recovery**.

[![Watch the Video Demo](https://img.youtube.com/vi/7lM06mos3EM/0.jpg)](https://youtube.com/shorts/7lM06mos3EM?feature=share)
