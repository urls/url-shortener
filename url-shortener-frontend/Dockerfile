FROM node:14-alpine3.14 AS build
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:1.19.0-alpine AS deployment
COPY --from=build /app/dist/ /usr/share/nginx/html
EXPOSE 80 443
CMD ["nginx", "-g", "daemon off;"]