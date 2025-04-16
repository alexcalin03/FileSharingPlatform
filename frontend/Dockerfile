# Stage 1: Build the React app
FROM node:16-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Serve the app using Nginx with a custom config
FROM nginx:alpine
# Remove the default Nginx configuration
RUN rm /etc/nginx/conf.d/default.conf
# Copy your custom configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf
# Copy the build output to Nginx's html directory
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
