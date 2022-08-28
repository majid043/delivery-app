
## Scenario:
BayzDelivery is a delivery startup which allows its users to register as delivery men or customers.

Customer gives an order from the online shop and delivery man picks the order and drives it to the customer.

At the end of delivery, delivery man sends a request to the server with his/her id, customer id, order id, the distance in km and the start and end time of delivery.

## Application Constraints:
- Users are using BayzDelivery mobile app, assume that the API is only consumed by mobile app
- Data should only be accepted from the registered customers and delivery men.
- User can not be both customer and delivery man at the same time. Only one must be chosen at registration
- The delivery man earns commission for each order. `TotalCommission = OrderPrice * 0.05 + Distance * 0.5`
- The delivery man is not allowed to deliver multiple orders at the same time
- API endpoint to display top 3 delivery men whose delivery has the maximum order price in a given time interval and they want to show average commission they earned.
- Customer support team wants to be notified when a delivery is not done in 45 minutes. Create the scheduled task to check and notify CS team asynchronously
- Unit test coverage to be 60%.

## Development Environment:
- GIT for version control
- Gradle
- Java 8
- MySQL 5.6+ (If required, change the DB connection url, user and password information by updating application.properties)
- Liquibase


