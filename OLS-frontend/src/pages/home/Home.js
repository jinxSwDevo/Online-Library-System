import React from "react";
import Books from "../books/Books";
import { Search } from "@mui/icons-material";
import Header from "../../components/Header";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";


const Home = ({ userId, books }) => {


  return ( 
    <div>
      <Books userId={userId} books={books}/>
    </div>
  );
};

export default Home;
