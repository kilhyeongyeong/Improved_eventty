FROM node:18-alpine
WORKDIR /app/front
COPY package.json ./
RUN npm install
COPY . ./
EXPOSE 3000
CMD ["npm", "run", "build"]