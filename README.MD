Prerequisites:
1. Have minikube installed (ref: https://kubernetes.io/docs/tasks/tools/install-minikube/) and running
2. Install HELM with (for MacOs `brew install kubernetes-helm`)
3. Add namespace to Kubernetes:
   ```shell script
   kubectl create -f jenkins-namespace.yaml
   ```
4. Create persistent volume for Jenkins;
    ```shell script
    kubectl create -f jenkins-volume.yaml
    ```
5. Create persistent volume claim for Jenkins:
    ```shell script
   kubectl create -f jenkins-pvc.yaml
    ```
6. Init HELM with `helm init`
7. Run following: 
    ```shell script
   helm install --name jenkins \
   --set persistence.existingClaim=jenkins-pvc \
   --set master.serviceType=NodePort \
   --set master.resources.requests.memory=256Mi \
   --set master.resources.limits.memory=1024Mi \
    stable/jenkins --namespace devops      
    ```
8. Get the password by running
    ```shell script
    printf $(kubectl get secret --namespace devops jenkins -o jsonpath="{.data.jenkins-admin-password}" | base64 --decode);echo
    ```
9. Get the connection URL by running these script
    ```shell script
    export NODE_PORT=$(kubectl get --namespace devops -o jsonpath="{.spec.ports[0].nodePort}" services jenkins)
    export NODE_IP=$(kubectl get nodes --namespace devops -o jsonpath="{.items[0].status.addresses[0].address}")
    echo http://$NODE_IP:$NODE_PORT/login
    ```
10. Login with username `admin` and password from point 8.
11. Update Jenkins URL in system properties to match the one from point 9.

To remove Jenkins setup, run `helm del --purge jenkins`