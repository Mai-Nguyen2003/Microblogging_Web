FROM node:23-alpine AS build
ARG TADA_API
ENV TADA_API $TADA_API
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY ./ ./
RUN echo "" > .env
RUN echo VITE_APP_TADA_API="${TADA_API}" >> .env
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build /usr/src/app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]