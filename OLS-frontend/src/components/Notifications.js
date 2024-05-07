import * as React from "react";
import Box from "@mui/joy/Box";
import Alert from "@mui/joy/Alert";
import Header from "./Header";

const Notifications = () => {
  return (
    <>

    <div>
      <Box sx={{ width: "100%", padding: "1rem" }}>
        <Alert>This is a basic Alert.</Alert>
      </Box>
      <Box sx={{ width: "100%", padding: "1rem" }}>
        <Alert>This is a basic Alert.</Alert>
      </Box>
    </div>

    </>
  );
 
};

export default Notifications;
