
-- 1. View all restaurant records
SELECT *
FROM zomato_restaurants
LIMIT 10;


-- 2. Count total restaurants
SELECT COUNT(*) AS total_restaurants
FROM zomato_restaurants;


-- 3. Find restaurants by city
SELECT city, COUNT(*) AS restaurant_count
FROM zomato_restaurants
GROUP BY city
ORDER BY restaurant_count DESC;


-- 4. Find popular cuisines
SELECT cuisines, COUNT(*) AS restaurant_count
FROM zomato_restaurants
GROUP BY cuisines
ORDER BY restaurant_count DESC
LIMIT 10;


-- 5. Find highly rated restaurants
SELECT *
FROM zomato_restaurants
WHERE rating >= 4.0
ORDER BY rating DESC;
