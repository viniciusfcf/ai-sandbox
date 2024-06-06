
# Deploy de um modelo que já foi gerado

Fonte: https://redhatquickcourses.github.io/rhods-deploy/rhods-deploy/1.33/chapter1/section2.html

- ```export DS_PROJECT_NAME=my-ds-project```
- ```export MODEL_NAME=my-model-name```
- ```export TOKEN=$(oc whoami -t)```
- ```export IRIS_ROUTE=https://$(oc get routes -n $DS_PROJECT_NAME | grep $MODEL_NAME | awk '{print $2}')```
```
curl -H "Authorization: Bearer $TOKEN" $IRIS_ROUTE/v2/models/$MODEL_NAME/infer -X POST --data '{"inputs" : [{"name" : "X","shape" : [ 1, 4 ],"datatype" : "FP32","data" : [ 3, 4, 3, 2 ]}],"outputs" : [{"name" : "output0"}]}'
```

# Fonte
- Passo a passo do modelo gerado: https://redhatquickcourses.github.io/rhods-deploy/rhods-deploy/1.33/chapter1/section2.html
- Notebook utilizado no passo a passo: https://github.com/RedHatQuickCourses/rhods-qc-apps/tree/main/4.rhods-deploy/chapter2