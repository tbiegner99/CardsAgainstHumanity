sudo docker run -it --rm --name certbot             -v "/etc/letsencrypt:/etc/letsencrypt"             -v "/var/lib/letsencrypt:/var/lib/letsencrypt" -v "$(pwd)/ui/nginx/certs:/etc/letsencrypt/live" -v "$(pwd)/ui/nginx/data/certbot/www:/var/webroot"             certbot/certbot certonly --webroot -w /var/webroot --test-cert -d tjbiegner.com -m tbiegner99@gmail.com -n --agree-tos
