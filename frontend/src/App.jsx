import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Navbar from "./common/Navbar";
import Footer from "./common/Footer";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Tasks from "./pages/Tasks";
import { AuthRoute } from "./guard/Guard"
import TaskForm from "./pages/TaskForm";


function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/register" element={ <Register /> } />
        <Route path="/login" element={ <Login /> } />

        <Route path="/tasks" element={ <AuthRoute element={ <Tasks /> } /> } />
        <Route path="/tasks/add" element={ <AuthRoute element={ <TaskForm /> } /> } />
        <Route path="/tasks/edit/:id" element={ <AuthRoute element={ <TaskForm /> } /> } />
        <Route path="*" element={ <Navigate to="/tasks" /> } />
      </Routes>

      <Footer />

    </BrowserRouter>
  )
}

export default App;
