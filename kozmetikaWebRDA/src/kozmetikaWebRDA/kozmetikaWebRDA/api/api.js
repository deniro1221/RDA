
const express=require('express');
const axios=require('axios');
const port=3000;
const app=express();
const apiUrl='http://makeup-api.herokuapp.com/api/v1/products.json';

app.get('/api/products',async(req,res)=>{
    try{
        const brand=req.query.brand;
        const name=req.query.name;
        const product_type=req.query.product_type;

        let url=apiUrl +'?';
        if (brand)url+=`brand=${brand}`;
        if(name)url+=`name=${name}`;
        if(product_type)url+=`product_type${product_type}`;

        const response=await axios.get(url);
        res.json(response.data)

    }catch(error){
        res.status(500).json({error:'server error'});
    }
});

app.use((req,res)=> {
    res.status(404).json({error:'not found'});
});

app.listen(port, () => {
    console.log(`server je pokrenut na portu : ${port}`);
});
